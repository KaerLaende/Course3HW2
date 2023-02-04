package ru.kaer.foodrecipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data // это аннотация которая говорит что будет автоматически сгенерированы сетерры и геттеры, и остальное.
@AllArgsConstructor// генерация конструктора со всеми аргументами
public class Recipes {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<String> steps = new LinkedList<>();
}
