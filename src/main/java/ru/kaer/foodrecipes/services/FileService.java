package ru.kaer.foodrecipes.services;

public interface FileService {

    boolean saveRecipesToFile(String json);

    String readRecipesFromFile();

    boolean cleanRecipesDataFile();

    boolean saveIngredientsToFile(String json);

    String readIngredientsFromFile();

    boolean cleanIngredientsDataFile();
}
