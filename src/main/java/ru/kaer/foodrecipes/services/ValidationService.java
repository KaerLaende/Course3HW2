package ru.kaer.foodrecipes.services;

import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.model.Recipes;

public interface ValidationService {
 public boolean validate(Recipes recipes);
 public boolean validate(Ingredient ingredient);

}
