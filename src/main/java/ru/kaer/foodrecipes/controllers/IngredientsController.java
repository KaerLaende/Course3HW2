package ru.kaer.foodrecipes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.services.IngredientsService;

@RestController
@RequestMapping("/ingridients")
public class IngredientsController {
    private IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    // добавление ингридиента в Мар
    @PostMapping
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = ingredientsService.addIngredient(ingredient);
        return ResponseEntity.ok(ingredient);
    }

    //получение ингридиента по ID
    @GetMapping("{ingredientId}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long ingredientId) {

        Ingredient ingredient = ingredientsService.getIngredient(ingredientId);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    //TODO     Исправить способ получение новых параметров
    //создание ингридиента
    @GetMapping("{ingredientName}/{count}/{measureUnit}")
    @ResponseBody
    public ResponseEntity<Ingredient> getIngredientFromPath(@RequestParam String param1, @RequestParam Integer param2, @RequestParam String param3) {

        if(param2 <=0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().build();
        } else {
            ingredientsService.createNewIngredient(param1, param2, param3);
            ResponseEntity.status(HttpStatus.OK);
            return ResponseEntity.ok().build();
        }
    }

    //показать все ингредиенты
    @GetMapping()
    public ResponseEntity<?> getAllIngredients() {
        return ResponseEntity.ok(ingredientsService.getAllIngredients());
    }

    // заменить/исправить конкретный ингредиент
    @PutMapping("{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable long id,@RequestBody Ingredient ingredient){
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        ingredientsService.editIngredient(id,ingredient);
        return ResponseEntity.ok(ingredient);
    }
    // удалить ингредиент
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id){
        if(ingredientsService.deleteIngredient(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
