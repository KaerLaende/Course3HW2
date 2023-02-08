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
        return
                "<h1> style=\"text-align: center\" Книга Рецептов</h1>"+
                        "<h4> Список рецептов с их ID(номер в списке):</h4>"+
                        "<ol><li>Сырники из творога." +
                        "<li>Салат из красной фасоли с творожным сыром, красным луком и сезонным салатом."+
                        "<li>Чечевичный суп с мятой." +
                        "<li>Салат с креветками и кунжутом."+
                        "<li>Творожные пончики в сахарной пудре." +
                        "</ol>"+"<h5> Введите в URL адресс - ID блюда, для знакомства с рецептом!</h5>"+

                '•'+" имя ученика: Вохминов Кирилл<br>" +'•'+
                " название проекта: FoodRecipes<br>" +'•'+
                " дата создания проекта: 31.01.2023г.<br>" +'•'+
                " описание проекта: рецепты.";
    }
}
