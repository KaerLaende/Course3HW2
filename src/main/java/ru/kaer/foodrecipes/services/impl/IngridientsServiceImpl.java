package ru.kaer.foodrecipes.services.impl;

import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.services.IngredientsService;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngridientsServiceImpl implements IngredientsService {
    Map<Long, Ingredient> ingredientMap = new TreeMap<>();
    private static long lastId = 0;


    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        ingredientMap.put( ++lastId, ingredient);
       return ingredient;
    }

    @Override
    public Ingredient getIngredient(Long id) {
        return ingredientMap.get(id);
    }

    @Override
    public Ingredient createNewIngredient(String name, int count, String measureUnit){
        Ingredient newIngredient =  new Ingredient(name, count, measureUnit);
        return  newIngredient;
    }
    @Override
    public Map<?,?> getAllIngredients(){
        return ingredientMap;
    }

    @Override
    public Ingredient editIngredient(Long id, Ingredient ingredient){
        if(ingredientMap.containsKey(id)){
            ingredientMap.put(id, ingredient);
            return ingredient;
        }
        return null;
    }
    @Override
    public boolean deleteIngredient(Long id){
        if(ingredientMap.containsKey(id)){
            ingredientMap.remove(id);
            return true;
        }
        return false;
    }



//    @Override
//    public Ingredient changeIngredient(Long id, String name){
//        allIgridientsMap.get(id).setIngridientName(name);
//        return allIgridientsMap.get(id);
//    }
//    @Override
//    public Ingredient changeIngredient(Long id, int count){
//        allIgridientsMap.get(id).setCount(count);
//        return allIgridientsMap.get(id);
//    }
//    @Override
//    public Ingredient changeIngredient(Long id, String name, int count, String measureUnit){
//        allIgridientsMap.get(id).setIngridientName(name);
//        allIgridientsMap.get(id).setCount(count);
//        allIgridientsMap.get(id).setMeasureUnit(measureUnit);
//        return allIgridientsMap.get(id);
//    }


}
