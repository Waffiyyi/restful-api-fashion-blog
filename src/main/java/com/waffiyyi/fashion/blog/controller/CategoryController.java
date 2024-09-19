package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.CategoryDTO;
import com.waffiyyi.fashion.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService cacategoryService;


  @PostMapping
  public ResponseEntity<CategoryDTO> createCategory(
     @RequestBody CategoryDTO categoryDTO) {
    CategoryDTO createdCategory = cacategoryService.saveCategory(categoryDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
    CategoryDTO categoryDTO = cacategoryService.findCategoryById(categoryId);
    return ResponseEntity.ok(categoryDTO);
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
    cacategoryService.deleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }

}
