package ru.kaer.foodrecipes.services;

import ru.kaer.foodrecipes.model.Recipes;

import java.util.Map;
import java.util.Optional;

public interface RecipesService {


    Recipes addRecipes(Recipes recipes);

    Optional<Recipes> getRecipes(Long id);

    Map<Long, Recipes> getAllRecipes();

    Recipes editRecipes(Long id, Recipes recipes);

    boolean deleteRecipes(Long id);

    void saveToFile();

    //TODO TypeReference подчеркивает красным (Type 'org.springframework.asm.TypeReference' does not have type parameters) HELP!?
    void readFromFile();
}
