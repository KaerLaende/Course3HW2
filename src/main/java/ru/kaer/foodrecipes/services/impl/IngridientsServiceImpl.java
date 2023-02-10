package ru.kaer.foodrecipes.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.asm.TypeReference;
import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.exceptions.ValidationException;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.services.FileService;
import ru.kaer.foodrecipes.services.IngredientsService;
import ru.kaer.foodrecipes.services.ValidationService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class IngridientsServiceImpl implements IngredientsService {
    private final TreeMap<Long, Ingredient> ingredientMap = new TreeMap<>();
    private static long lastId = 0;
    private final ValidationService validationService;
    private final FileService fileService;


    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredientMap.put(++lastId, ingredient);
        saveToFile();
        return ingredient;
    }

    @Override
    public Optional<Ingredient> getIngredient(Long id) {

        return Optional.ofNullable(ingredientMap.get(id));
    }

    @Override
    public Ingredient createNewIngredient(String name, int count, String measureUnit) {
        Ingredient newIngredient = new Ingredient(name, count, measureUnit);
        saveToFile();
        return newIngredient;
    }

    @Override
    public Map<Long, Ingredient> getAllIngredients() {
        return ingredientMap;
    }

    @Override
    public Ingredient editIngredient(Long id, Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredientMap.replace(id, ingredient);
        saveToFile();
        return ingredient;
    }

    @Override
    public boolean deleteIngredient(Long id) {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }
    @Override
    public void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            fileService.saveIngredientsToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    //TODO TypeReference подчеркивает красным (Type 'org.springframework.asm.TypeReference' does not have type parameters) HELP!?
    public void readFromFile() {
        try {
            String json = fileService.readIngredientsFromFile();
            ingredientMap = new ObjectMapper().readValue(json,
                    new TypeReference<TreeMap<Long, Ingredient >> () {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
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
