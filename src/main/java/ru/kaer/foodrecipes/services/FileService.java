package ru.kaer.foodrecipes.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface FileService {

    boolean saveRecipesToFile(String json);

    String readRecipesFromFile();

    boolean cleanRecipesDataFile();

    boolean saveIngredientsToFile(String json);

    String readIngredientsFromFile();

    boolean cleanIngredientsDataFile();

    File getIngredientDataFile();

    File getRecipesDataFile();

    void importRecipeDataFile(MultipartFile file) throws IOException;

    void importIngredientDataFile(MultipartFile file) throws IOException;

    Path createTempFile(String suffix);

    Path createRecipeTextFile() throws IOException;
}
