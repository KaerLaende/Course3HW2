package ru.kaer.foodrecipes.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.services.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Value(value = "${path.to.data.files}")
    private String dataFilePath;
    @Value(value = "${name.of.recipe.data.file}")
    private String getRecipesDataFileName;
//    private final Path pathRecipes = Path.of(dataFilePath,getRecipesDataFileName);
    @Value(value = "${name.of.ingredient.data.file}")
    private String getIngredientsDataFileName;
//    private final Path pathIngredients = Path.of(dataFilePath,getIngredientsDataFileName);

    @Override
    public boolean saveRecipesToFile(String json){
        try {
            cleanRecipesDataFile();
            Files.writeString(Path.of(dataFilePath,getRecipesDataFileName),json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String readRecipesFromFile(){
        try {
            return  Files.readString(Path.of(dataFilePath,getRecipesDataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean cleanRecipesDataFile(){
        try {
            Files.deleteIfExists(Path.of(dataFilePath,getRecipesDataFileName));
            Files.createFile(Path.of(dataFilePath,getRecipesDataFileName));
            return true;
        } catch (IOException e) {
            return  false;
        }

    }
    @Override
    public boolean saveIngredientsToFile(String json){
        try {
            cleanRecipesDataFile();
            Files.writeString(Path.of(dataFilePath,getIngredientsDataFileName),json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String readIngredientsFromFile(){
        try {
            return  Files.readString(Path.of(dataFilePath,getIngredientsDataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean cleanIngredientsDataFile(){
        try {
            Files.deleteIfExists(Path.of(dataFilePath,getIngredientsDataFileName));
            Files.createFile(Path.of(dataFilePath,getIngredientsDataFileName));
            return true;
        } catch (IOException e) {
            return  false;
        }
    }
    @Override
    public File getIngredientDataFile(){
        return new File(dataFilePath+ "/"+ getIngredientsDataFileName);
    }
    @Override
    public File getRecipesDataFile(){
        return new File(dataFilePath+ "/"+getRecipesDataFileName);
    }

}
