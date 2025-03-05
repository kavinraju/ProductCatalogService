package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/category")
public class CategoryController {
    @GetMapping("/{id}")
    public CategoryDto getCategoryDetails(@PathVariable Long id) {
        CategoryDto Category = new CategoryDto();
        Category.setId(id);
        Category.setName("Helllo name");
        return Category;
    }

    @GetMapping()
    public List<CategoryDto> getCategorys() {
        CategoryDto Category = new CategoryDto();
        Category.setName("Helllo name");
        return List.of(Category);
    }

    @PostMapping()
    public CategoryDto createCategory(@RequestBody CategoryDto CategoryDto) {
        return null;
    }

    @PatchMapping("/{id}")
    public CategoryDto updateCategory(@RequestParam Long id, @RequestBody CategoryDto CategoryDto) {
        return null;
    }

    @DeleteMapping()
    public boolean deleteCategory() {
        return false;
    }
}
