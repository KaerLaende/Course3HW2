package ru.kaer.foodrecipes.services.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.exceptions.ValidationException;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.services.IngredientsService;
import ru.kaer.foodrecipes.services.ValidationService;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class IngridientsServiceImpl implements IngredientsService {
    private final Map<Long, Ingredient> ingredientMap = new TreeMap<>();
    private static long lastId = 0;
    private final ValidationService validationService;


    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!validationService.validate(ingredient)){
        throw new ValidationException(ingredient.toString());
        }
        return ingredientMap.put( ++lastId, ingredient);
    }

    @Override
    public Optional<Ingredient> getIngredient(Long id) {

        return Optional.ofNullable(ingredientMap.get(id));
    }

    @Override
    public Ingredient createNewIngredient(String name, int count, String measureUnit){
        Ingredient newIngredient =  new Ingredient(name, count, measureUnit);
        return  newIngredient;
    }
    @Override
    public Map<Long, Ingredient> getAllIngredients(){
        return ingredientMap;
    }

    @Override
    public Ingredient editIngredient(Long id, Ingredient ingredient){
        if(!validationService.validate(ingredient)){
            throw new ValidationException(ingredient.toString());
        }
        return  ingredientMap.replace(id, ingredient);
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
