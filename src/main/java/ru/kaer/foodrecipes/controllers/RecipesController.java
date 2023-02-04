package ru.kaer.foodrecipes.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.RecipesMapService;

@RestController
@RequestMapping("/recipes")
public class RecipesController {
    private RecipesMapService recipesMapService;

    public RecipesController(@Qualifier("recipesMapServiceImpl") RecipesMapService recipesMapService) {
        this.recipesMapService = recipesMapService;
    }

    @PostMapping
    public ResponseEntity addRecipes(@RequestBody Recipes recipes) {
        Recipes createdRecipes = recipesMapService.addRecipes(recipes);
        return ResponseEntity.ok(recipes);
    }// не внимательно прочитал задание по контроллерам и пришлось помучится... потом понял что это не надо было :(

    @GetMapping("{recipesId}")
    public ResponseEntity getRecipes(@PathVariable Long recipesId) {

        Recipes recipes = recipesMapService.getRecipes(recipesId);
        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }


}


