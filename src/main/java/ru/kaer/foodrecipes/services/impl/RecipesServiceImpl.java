package ru.kaer.foodrecipes.services.impl;

import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.RecipesService;

import java.util.Map;
import java.util.TreeMap;
@Service
public class RecipesServiceImpl implements RecipesService {
    Map<Long, Recipes> allRecipesMap = new TreeMap<>();
    private static long lastId = 0;

    @Override
    public Recipes addRecipes(Recipes recipes) {
        allRecipesMap.put(++lastId, recipes);
        return recipes;
    }
    @Override
    public Recipes getRecipes(Long id){
        allRecipesMap.get(id);
        return null;
    }
    @Override
    public Map<?,?> getAllRecipes(){
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
