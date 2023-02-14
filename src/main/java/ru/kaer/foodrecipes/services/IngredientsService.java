package ru.kaer.foodrecipes.services;

import ru.kaer.foodrecipes.model.Ingredient;

import java.util.Map;
import java.util.Optional;

public interface IngredientsService {


    Ingredient addIngredient(Ingredient ingredient);

    Optional<Ingredient> getIngredient(Long id);

    Ingredient createNewIngredient(String name, int count, String measureUnit);

    Map<Long, Ingredient> getAllIngredients();

    Ingredient editIngredient(Long id, Ingredient ingredient);

    boolean deleteIngredient(Long id);

    void saveToFile();

    void readFromFile();
}
