package de.schnellertermin.backend.controllers;

import de.schnellertermin.backend.services.abstracts.CategoryService;
import de.schnellertermin.backend.services.dtos.requests.CategoryRequest;
import de.schnellertermin.backend.services.dtos.responses.CategoryCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.CategoryGetAllResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    List<CategoryGetAllResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    CategoryCreatedResponse getCategoryById(@PathVariable String id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryCreatedResponse addCategory (@Valid @RequestBody CategoryRequest categoryRequest){
        return categoryService.addCategory(categoryRequest);
    }
}
