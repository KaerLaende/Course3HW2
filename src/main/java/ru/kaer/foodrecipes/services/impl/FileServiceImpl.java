package ru.kaer.foodrecipes.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.services.FileService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class FileServiceImpl implements FileService {

    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.to.recipe.data.file}")
    private String getRecipesDataFileName;
    private final Path pathRecipes = Path.of(dataFilePath,getRecipesDataFileName);
    @Value("${name.to.ingredient.data.file}")
    private String getIngredientsDataFileName;
    private final Path pathIngredients = Path.of(dataFilePath,getIngredientsDataFileName);

    @Override
    public boolean saveRecipesToFile(String json){
        try {
            cleanRecipesDataFile();
            Files.writeString(pathRecipes,json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String readRecipesFromFile(){
        try {
            return  Files.readString(pathRecipes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean cleanRecipesDataFile(){
        try {
            Files.deleteIfExists(pathRecipes);
            Files.createFile(pathRecipes);
            return true;
        } catch (IOException e) {
            return  false;
        }

    }
    @Override
    public boolean saveIngredientsToFile(String json){
        try {
            cleanRecipesDataFile();
            Files.writeString(pathIngredients,json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String readIngredientsFromFile(){
        try {
            return  Files.readString(pathIngredients);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean cleanIngredientsDataFile(){
        try {
            Files.deleteIfExists(pathIngredients);
            Files.createFile(pathIngredients);
            return true;
        } catch (IOException e) {
            return  false;
        }
    }

}
