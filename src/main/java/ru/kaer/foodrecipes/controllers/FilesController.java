package ru.kaer.foodrecipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kaer.foodrecipes.services.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/files")
public class FilesController {
    private final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }
    @GetMapping("/export")
    @Operation(
            summary = "загрузить файлы с рецептами и ингридиентами")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "файлы загружены"
            )})
    public void downloadDataFile() throws FileNotFoundException {
        downloadIngredient();
        downloadRecipes();
    } // хотел сделать что бы 1 юрл загружал сразу оба файла - так можно сделать?
    public ResponseEntity<InputStreamResource> downloadRecipes() throws FileNotFoundException {
        File file = fileService.getRecipesDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipesLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
        public ResponseEntity<InputStreamResource> downloadIngredient() throws FileNotFoundException {
        File file = fileService.getIngredientDataFile();
        if (file.exists()){
            InputStreamResource resource= new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ingredientLog.json\"")
                    .body(resource);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }


}
