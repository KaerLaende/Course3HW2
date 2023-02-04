package ru.kaer.foodrecipes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.IngredientsMapService;

@RestController
@RequestMapping("/ingridients")
public class IngredientsController {
    private IngredientsMapService ingredientsMapService;

    public IngredientsController(IngredientsMapService ingredientsMapService) {
        this.ingredientsMapService = ingredientsMapService;
    }

    // добавление ингридиента в Мар
    @PostMapping
    public ResponseEntity addIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = ingredientsMapService.addIngredient(ingredient);
        return ResponseEntity.ok(ingredient);
    }

    //получение ингридиента по ID
    @GetMapping("{ingredientId}")
    public ResponseEntity getIngredient(@PathVariable Long ingredientId) {

        Ingredient ingredient = ingredientsMapService.getIngredient(ingredientId);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    //создание ингридиента
    @GetMapping("{ingredientName}/{count}/{measureUnit}")
    @ResponseBody
    public ResponseEntity<?> getIngredientFromPath(@RequestParam String param1, @RequestParam Integer param2, @RequestParam String param3) {

        if(param1 == null || param2 <=0|| param3==null) {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST); //IDEA подсказала - (ResponseEntity<?>)
        } else {
             ingredientsMapService.createNewIngredient(param1, param2, param3);
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK); // Иначе ругалось на возвращаемое значение, не понял почему...
        }
    }

}
