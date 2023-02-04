package ru.kaer.foodrecipes.services.impl;

import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.services.IngredientsMapService;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngridientsMapServiceImpl implements IngredientsMapService {
    Map<Long, Ingredient> allIgridientsMap = new TreeMap<>();
    private static long lastId = 0;


    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        allIgridientsMap.put( ++lastId, ingredient);
       return ingredient;
    }

    @Override
    public Ingredient getIngredient(Long id) {
        return allIgridientsMap.get(id);
    }

    @Override
    public Ingredient createNewIngredient(String name, int count, String measureUnit){
        Ingredient newIngredient =  new Ingredient(name, count, measureUnit);
        return  newIngredient;
    }

}
