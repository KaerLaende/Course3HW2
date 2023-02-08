package ru.kaer.foodrecipes.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.RecipesService;

import java.util.Map;

@RestController
@RequestMapping("/recipes")
public class RecipesController {
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @PostMapping
    public ResponseEntity<Recipes> addRecipes(@RequestBody Recipes recipes) {
        Recipes createdRecipes = recipesService.addRecipes(recipes);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("{recipesId}")
    public ResponseEntity<Recipes> getRecipes(@PathVariable Long recipesId) {

        Recipes recipes = recipesService.getRecipes(recipesId);
        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @GetMapping()
    public ResponseEntity<Map> getAllRecipes() {
        return ResponseEntity.ok(recipesService.getAllRecipes());
    }

    @PutMapping("{id}")
    public ResponseEntity<Recipes> editRecipes(@PathVariable long id, @RequestBody Recipes recipes) {
        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        recipesService.editRecipes(id, recipes);
        return ResponseEntity.ok(recipes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipes(@PathVariable long id) {
        if (recipesService.deleteRecipes(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}


