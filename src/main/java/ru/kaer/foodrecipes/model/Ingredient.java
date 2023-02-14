package ru.kaer.foodrecipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ingredient {
    private String ingredientName;
    private int count;
    private String measureUnit;

    @Override
    public String toString() {
        return  ingredientName + '-' + count + ' '+ measureUnit+'.';
    }
}

