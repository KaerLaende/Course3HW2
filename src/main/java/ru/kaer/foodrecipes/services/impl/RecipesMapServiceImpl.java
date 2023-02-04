package ru.kaer.foodrecipes.services.impl;

import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.RecipesMapService;

import java.util.Map;
import java.util.TreeMap;
@Service
public class RecipesMapServiceImpl implements RecipesMapService {
    Map<Integer, Recipes> allRecipesMap = new TreeMap<>();
    private static long lastId = 0;

    @Override
    public Recipes addRecipes(Recipes recipes) {
        allRecipesMap.put((int) ++lastId, recipes);
        return recipes;
    }
    @Override
    public Recipes getRecipes(Long id){
        allRecipesMap.get(id);
        return null;
    }


}
