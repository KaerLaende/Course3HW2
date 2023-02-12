package ru.kaer.foodrecipes.services;

import java.io.File;

public interface FileService {

    boolean saveRecipesToFile(String json);

    String readRecipesFromFile();

    boolean cleanRecipesDataFile();

    boolean saveIngredientsToFile(String json);

    String readIngredientsFromFile();

    boolean cleanIngredientsDataFile();

    File getIngredientDataFile();

    File getRecipesDataFile();
}
