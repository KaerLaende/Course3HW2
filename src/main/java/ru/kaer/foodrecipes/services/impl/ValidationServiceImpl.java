package ru.kaer.foodrecipes.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {

    public boolean validate(Ingredient ingredient){
        return  !StringUtils.isEmpty(ingredient.getIngredientName())
                && !StringUtils.isBlank(ingredient.getIngredientName());
    }

    public boolean validate(Recipes recipes){
       return recipes!=null
               &&!StringUtils.isEmpty(recipes.getName())
               &&!StringUtils.isBlank(recipes.getName())
               &&recipes.getIngredients()!=null
               &&recipes.getSteps()!=null
               &&!recipes.getIngredients().isEmpty();
    }

}
