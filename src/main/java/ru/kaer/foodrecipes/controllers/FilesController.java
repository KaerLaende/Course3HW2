package ru.kaer.foodrecipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kaer.foodrecipes.services.FileService;
import ru.kaer.foodrecipes.services.impl.FileServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файлы", description = "сохранить/загрузить")
public class FilesController {
    private final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/exportRecipes")
    @Operation(
            summary = "загрузить файл с рецептами")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "файл загружен"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "файла нет"
            )
    })
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

    /**
     * Эндпоинт загрузки и замены файла с рецептами
     *
     * @param file json с рецептами
     * @return заменяет сохраненный на жестком (локальном) диске файл с рецептами на новый
     */
    @PostMapping(value = "/importRecipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile file) {
        try {
            fileService.importRecipeDataFile(file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Эндпоинт загрузки и замены файла, с использованием бибилиотеки 'Apache Commons IO'
     *
     * @param file json с с ингредиентами
     * @return заменяет сохраненный на жестком (локальном) диске файл с ингредиентами на новый
     */
    @PostMapping(value = "/importIngredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) { //получаем файл
        try {
            fileService.importIngredientDataFile(file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // если что то пошло не так отправляем HttpStatus
    }

    /**
     * Эндпоинт выгрузки рецептов в текстовый файл
     * @return файл с рецептами.txt
     */
    @GetMapping("exportInText")
    @Operation(
            summary = "Экспорт рецептов в файл в формате текста"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешно выгрузили текстовый файл",
            content = {
                    @Content(
                            mediaType = "text/plain"
                    )
            }
    )
    public ResponseEntity<Object> getRecipeTextFile() {
        try {
            Path path = fileService.createRecipeTextFile();
            if (Files.size(path) != 0) {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .contentLength(Files.size(path))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes.txt\"")
                        .body(resource);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }

    }

}
