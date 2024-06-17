package de.schnellertermin.backend.controllers;

import de.schnellertermin.backend.services.abstracts.CategoryService;
import de.schnellertermin.backend.services.dtos.requests.CategoryRequest;
import de.schnellertermin.backend.services.dtos.responses.CategoryCreatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryCreatedResponse addCategory (@RequestBody CategoryRequest categoryRequest){
        return categoryService.addCategory(categoryRequest);
    }
}
