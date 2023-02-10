package ru.kaer.foodrecipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kaer.foodrecipes.model.Recipes;
import ru.kaer.foodrecipes.services.RecipesService;

import java.util.Map;

@RestController
@RequestMapping("/recipes")
@Tag(name= "Рецепты", description = "CRUD - операции по работе с рецептами.")
public class RecipesController {
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @PostMapping
    @Operation(
            summary = "Добавление рецепта в Мар"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "рецепт был успешно добавлен"
            )
    })
    public ResponseEntity<Recipes> addRecipes(@RequestBody Recipes recipes) {
        Recipes createdRecipes = recipesService.addRecipes(recipes);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("{recipesId}")
    @Operation(
            summary = "Получение рецепта по его ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "рецепт был успешно найден"
            )
    })
    public ResponseEntity<Recipes> getRecipes(@PathVariable Long recipesId) {
            return ResponseEntity.of(recipesService.getRecipes(recipesId));

    }

    @GetMapping()
    @Operation(
            summary = "Получение всех имеющихся рецептов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "отображенны все рецепты"
            )
    })
    public ResponseEntity<Map<Long,Recipes>> getAllRecipes() {
        return ResponseEntity.ok(recipesService.getAllRecipes());
    }

    @PutMapping("{id}")
    @Operation(
            summary = "изменить существующий рецепт",
            description = "введите ID номер рецепта, которого хотите изменить"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "рецепт изменен"
            )
    })
    public ResponseEntity<Recipes> editRecipes(@PathVariable long id, @RequestBody Recipes recipes) {
        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        recipesService.editRecipes(id, recipes);
        return ResponseEntity.ok(recipes);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "удалить существующий рецепт",
            description = "введите ID номер рецепта, который хотите удалить"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "рецепт удален"
            )
    })
    public ResponseEntity<Void> deleteRecipes(@PathVariable long id) {
        if (recipesService.deleteRecipes(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}


