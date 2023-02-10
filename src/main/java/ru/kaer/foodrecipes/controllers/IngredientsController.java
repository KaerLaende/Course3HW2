package ru.kaer.foodrecipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kaer.foodrecipes.model.Ingredient;
import ru.kaer.foodrecipes.services.IngredientsService;

import java.util.Map;

@RestController
@RequestMapping("/ingredients")
@Tag(name= "Ингредиенты", description = "CRUD - операции по работе с ингредиентами, которые нам понадобятся для рецептов.")
public class IngredientsController {
    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    // добавление ингредиента в Мар
    @PostMapping
    @Operation(
            summary = "Добавление ингредиентов в Мар",
            description = "ингредиенты состоят из: наименования, колличества и единицы измерения"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ингредиент был успешно добавлен"
            )
    })
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = ingredientsService.addIngredient(ingredient);
        return ResponseEntity.ok(ingredient);
    }

    //получение ингридиента по ID
    @GetMapping("{ingredientId}")
    @Operation(
            summary = "получение ингредиента по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ингредиент был найден"
            )
    })
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long ingredientId) {

        return ResponseEntity.of(ingredientsService.getIngredient(ingredientId));
    }


    //создание ингридиента
    @PostMapping("/new")
    @ResponseBody
    @Operation(
            summary = "Создание нового ингредиента",
            description = "ингредиенты состоят из: наименования, колличества и единицы измерения"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ингредиент был успешно создан"
            )
    })
    public ResponseEntity<Ingredient> getIngredientFromPath(@RequestParam(required = false) String param1, @RequestParam  Integer param2, @RequestParam(required = false) String param3) {

        if(param1 == null || param2 <=0|| param3==null) {
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
    @Operation(
            summary = "Показ всех ингредиентов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ингредиенты показаны"
            )
    })
    public ResponseEntity<Map<Long,Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientsService.getAllIngredients());
    }

    // заменить/исправить конкретный ингредиент
    @PutMapping("{id}")
    @Operation(
            summary = "заменить/исправить конкретный ингредиент"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ингредиент исправлен"
            )
    })
    public ResponseEntity<Ingredient> editIngredient(@PathVariable long id,@RequestBody Ingredient ingredient){
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        ingredientsService.editIngredient(id,ingredient);
        return ResponseEntity.ok(ingredient);
    }
    // удалить ингредиент
    @DeleteMapping("{id}")
    @Operation(
            summary = "удалить ингредиент",
            description = "Укажите ID ингредиента, которого вы хотите удалить"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ингредиент удален"
            )
    })
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id){
        if(ingredientsService.deleteIngredient(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
