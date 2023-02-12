package ru.kaer.foodrecipes.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.exceptions.ValidationException;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.FileService;
import ru.kaer.foodrecipes.services.RecipesService;
import ru.kaer.foodrecipes.services.ValidationService;

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
            String json = new ObjectMapper().writeValueAsString(allRecipesMap);
            fileService.saveRecipesToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFromFile(){
       try {
           String json = fileService.readRecipesFromFile();
           allRecipesMap = new ObjectMapper().readValue(json, new TypeReference<>() {
           });
       } catch (JsonProcessingException e) {
           e.printStackTrace();
       }
    }
    /**
     * Загрузка данных на старте приложения
     */
    @PostConstruct
    private void init() {
      readFromFile();
    }



}
