package ru.kaer.foodrecipes.services;

import ru.kaer.foodrecipes.model.Ingredient;

import java.util.Map;

public interface IngredientsService {


    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(Long id);

    Ingredient createNewIngredient(String name, int count, String measureUnit);

    Map<?,?> getAllIngredients();

    Ingredient editIngredient(Long id, Ingredient ingredient);

    boolean deleteIngredient(Long id);
}
