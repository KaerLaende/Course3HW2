package ru.kaer.foodrecipes.services.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.FileService;
import ru.kaer.foodrecipes.services.RecipesService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class FileServiceImpl implements FileService {

    @Value(value = "${path.to.data.files}")
    private String dataFilePath;
    @Value(value = "${name.of.recipe.data.file}")
    private String getRecipesDataFileName;
    @Value(value = "${name.of.ingredient.data.file}")
    private String getIngredientsDataFileName;
    RecipesService recipesService;


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

    @Override
    public void importRecipeDataFile(MultipartFile file) throws IOException {
        cleanRecipesDataFile();//удаляем дата файл и создаем пустой новый
        File dataFile = getRecipesDataFile();// в новый берем информацию из RecipesDataFile
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {  // открываем исходящий поток
            IOUtils.copy(file.getInputStream(), fos); // берем входящий поток из @RequestParam и копируем в исходящий поток  'fos'
        } catch (IOException e) {
            throw new IOException();
        }
    }
    @Override
    public void importIngredientDataFile(MultipartFile file) throws IOException {
        cleanIngredientsDataFile();
        File dataFile = getIngredientDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            throw new IOException();
        }
    }



    @Override
    public Path createTempFile(String suffix){
        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Path createRecipeTextFile() throws IOException {
        Path path = createTempFile("recipes");
        for (Recipes recipe : recipesService.getAllRecipes().values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(recipe.toString());
                writer.append("\n");
            }
        }
        return path;
    }



}
