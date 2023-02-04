package ru.kaer.foodrecipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ingredient {
    private String ingridientName;
    private int count;
    private String measureUnit;

}

