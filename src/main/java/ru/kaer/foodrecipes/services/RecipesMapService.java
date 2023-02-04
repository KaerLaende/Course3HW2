package ru.kaer.foodrecipes.services;

import ru.kaer.foodrecipes.model.Recipes;

public interface RecipesMapService {


    Recipes addRecipes(Recipes recipes);

    Recipes getRecipes(Long id);
}
