package ru.kaer.foodrecipes.services;

import ru.kaer.foodrecipes.model.Recipes;

import java.util.Map;

public interface RecipesService {


    Recipes addRecipes(Recipes recipes);

    Recipes getRecipes(Long id);

    Map<Long, Recipes> getAllRecipes();

    Recipes editRecipes(Long id, Recipes recipes);

    boolean deleteRecipes(Long id);
}
