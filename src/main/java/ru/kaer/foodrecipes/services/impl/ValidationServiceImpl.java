package ru.kaer.foodrecipes.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.model.Recipes;

@Service
public class ValidationServiceImpl {

    public boolean validate(Ingredient ingredient){
        return ingredient.getIngredientName() != null
                && StringUtils.isEmpty(ingredient.getIngredientName())
                && StringUtils.isBlank(ingredient.getIngredientName());
    }

    public boolean validate(Recipes recipes){
       return recipes!=null
               &&StringUtils.isEmpty(recipes.getName())
               &&StringUtils.isBlank(recipes.getName())
               &&recipes.getIngredients()!=null
               &&recipes.getSteps()!=null
               &&!recipes.getIngredients().isEmpty();
    }

}
