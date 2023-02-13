package ru.kaer.foodrecipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
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
import ru.kaer.foodrecipes.services.impl.FileServiceImpl;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файлы", description = "сохранить/загрузить")
public class FilesController {
    private final FileServiceImpl fileService;

    public FilesController(FileServiceImpl fileService) {
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
     * @param file json с рецептами
     * @return заменяет сохраненный на жестком (локальном) диске файл с рецептами на новый
     */
    @PostMapping("/importRecipes")
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile file) { //получаем файл
        fileService.cleanRecipesDataFile(); //удаляем дата файл и создаем пустой новый
        File fileRecipes = fileService.getRecipesDataFile(); // берем для него информацию из RecipesDataFile

        try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream()); // открываем buffered потоки
             FileOutputStream fos = new FileOutputStream(fileRecipes);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] buffer = new byte[1024]; // определяем размер буфера
            while(bis.read(buffer)>0){ // пока все не считаем
                bos.write(buffer); // записываем
            }
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Эндпоинт загрузки и замены файла, с использованием бибилиотеки 'Apache Commons IO'
     * @param file json с с ингредиентами
     * @return заменяет сохраненный на жестком (локальном) диске файл с ингредиентами на новый
     */
    @PostMapping(value = "/importIngredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) { //получаем файл
        fileService.cleanIngredientsDataFile(); //удаляем дата файл и создаем пустой новый
        File fileIngredients = fileService.getIngredientDataFile(); // берем для него информацию из RecipesDataFile
        try(FileOutputStream fos = new FileOutputStream(fileIngredients)){ // открываем исходящий поток
            IOUtils.copy(file.getInputStream(),fos); // берем входящий поток из @RequestParam и копируем в исходящий поток  'fos'
            return ResponseEntity.ok().build(); // OK, если все успешно
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // если что то пошло не так отправляем HttpStatus
    }


//    public ResponseEntity<InputStreamResource> downloadRecipes() throws FileNotFoundException {
//        File file = fileService.getRecipesDataFile();
//        if (file.exists()) {
//            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .contentLength(file.length())
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipesLog.json\"")
//                    .body(resource);
//        } else {
//            return ResponseEntity.noContent().build();
//        }
//    }
//        public ResponseEntity<InputStreamResource> downloadIngredient() throws FileNotFoundException {
//        File file = fileService.getIngredientDataFile();
//        if (file.exists()){
//            InputStreamResource resource= new InputStreamResource(new FileInputStream(file));
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .contentLength(file.length())
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ingredientLog.json\"")
//                    .body(resource);
//        }
//        else {
//            return ResponseEntity.noContent().build();
//        }
//    }


}
