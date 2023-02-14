package ru.kaer.foodrecipes.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.exceptions.ValidationException;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.FileService;
import ru.kaer.foodrecipes.services.RecipesService;
import ru.kaer.foodrecipes.services.ValidationService;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
@Service
@Data
@AllArgsConstructor
public class RecipesServiceImpl implements RecipesService {
    private static TreeMap<Long, Recipes> allRecipesMap = new TreeMap<>();
    private static long lastId = 0;
    private final ValidationService validationService;
    private final FileService fileService;

    @Override
    public Recipes addRecipes(Recipes recipes) {
        if (!validationService.validate(recipes)){
            throw new ValidationException(recipes.toString());
        }
        allRecipesMap.put( ++lastId, recipes);
        saveToFile();
        return recipes;
    }

    @Override
    public Optional<Recipes> getRecipes(Long id) {

        return Optional.ofNullable(allRecipesMap.get(id));
    }
    @Override
    public Map<Long, Recipes> getAllRecipes(){
        return allRecipesMap;
    }

    @Override
    public Recipes editRecipes(Long id, Recipes recipes){
        if(allRecipesMap.containsKey(id)){
            allRecipesMap.put(id, recipes);
            saveToFile();
            return recipes;
        }
        return null;
    }
    @Override
    public boolean deleteRecipes(Long id){
        if(allRecipesMap.containsKey(id)){
            allRecipesMap.remove(id);
            return true;
        }
        return false;
    }
    @Override
    public void saveToFile(){
        try {
            DataFile dataFile = new DataFile(lastId+1,allRecipesMap);
            String json = new ObjectMapper().writeValueAsString(dataFile);
            fileService.saveRecipesToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFromFile(){
       try {
           String json = fileService.readRecipesFromFile();

           DataFile dataFile = new ObjectMapper().readValue(json, new TypeReference<DataFile>() {
           });
           lastId = dataFile.getLastId();
           allRecipesMap= dataFile.getAllRecipesMap();
       } catch (JsonProcessingException e) {
           e.printStackTrace();
       }
    }
    /**
     * Загрузка данных на старте приложения
     */
//    @PostConstruct
//    private void init() {
//        try{
//            readFromFile();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//
//    public void addRecipesFromInputStream(InputStream inputStream) throws IOException {
//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
//            String line ;
//            while((line= reader.readLine())!=null){
//                String[] array = StringUtils.split(line, '|');
//                new Recipes()
//            }
//        }
//    }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
    private static class DataFile {
        private long lastId;
        private TreeMap<Long,Recipes> allRecipesMap;
    }
}
