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
    private int numberOfServings;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<String> steps = new LinkedList<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(name.toUpperCase()).append("\n");
        stringBuilder.append("Время готовки: ").append(cookingTime).append(" минут\n");
        stringBuilder.append("Количество порций: ").append(numberOfServings).append(" шт.\n");
        stringBuilder.append("Ингредиенты:\n");
        for (Ingredient ingredient:ingredients) {
            stringBuilder.append("• ").append(ingredient).append("\n");
        }
        stringBuilder.append("Инструкция по приготовлению:\n");

        for (int i = 0; i < steps.size(); i++) {
            stringBuilder.append(i + 1).append(" - ").append(steps.get(i)).append("\n");
        }
        return stringBuilder.toString();
    }
}
