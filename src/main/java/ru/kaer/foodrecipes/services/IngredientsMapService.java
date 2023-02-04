package ru.kaer.foodrecipes.services;

import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.model.Recipes;

public interface IngredientsMapService {


    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(Long id);

    Ingredient createNewIngredient(String name, int count, String measureUnit);
}
