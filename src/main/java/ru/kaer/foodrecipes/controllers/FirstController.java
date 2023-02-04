package ru.kaer.foodrecipes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
@GetMapping
    public String printHello(){
        return "Привет! Приложение запущено";
    }
    @GetMapping("/info")
    public String printInfo(){
        return '•'+" имя ученика: Вохминов Кирилл\n" +'•'+
                " название проекта: FoodRecipes\n" +'•'+
                " дата создания проекта: 31.01.2023г.\n" +'•'+
                " описание проекта: рецепты.";
    }//пока все строчки выводятся в 1 строчку в браузере...
}
