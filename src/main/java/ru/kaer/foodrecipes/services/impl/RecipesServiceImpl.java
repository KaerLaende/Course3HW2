package ru.kaer.foodrecipes.services.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.exceptions.ValidationException;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.RecipesService;
import ru.kaer.foodrecipes.services.ValidationService;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
@Service
@RequiredArgsConstructor
public class RecipesServiceImpl implements RecipesService {
    private final Map<Long, Recipes> allRecipesMap = new TreeMap<>();
    private static long lastId = 0;
    private final ValidationService validationService;

    @Override
    public Recipes addRecipes(Recipes recipes) {
        if (!validationService.validate(recipes)){
            throw new ValidationException(recipes.toString());
        }
        return allRecipesMap.put( ++lastId, recipes);
    }

    @Override
    public Optional<Recipes> getRecipes(Long id) {

        return Optional.ofNullable(allRecipesMap.get(id));
    }
    @Override
    public Map<Long, Recipes> getAllRecipes(){
        return allRecipesMap;
    }

    @Override
    public Recipes editRecipes(Long id, Recipes recipes){
        if(allRecipesMap.containsKey(id)){
            allRecipesMap.put(id, recipes);
            return recipes;
        }
        return null;
    }
    @Override
    public boolean deleteRecipes(Long id){
        if(allRecipesMap.containsKey(id)){
            allRecipesMap.remove(id);
            return true;
        }
        return false;
    }



}
