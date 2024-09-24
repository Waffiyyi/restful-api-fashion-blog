package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.CategoryDTO;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.service.CategoryService;
import com.waffiyyi.fashion.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService cacategoryService;
  private final UserService userService;


  @PostMapping
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO,
                                                    @RequestHeader("Authorization")
                                                    String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    CategoryDTO createdCategory = cacategoryService.saveCategory(categoryDTO, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
    CategoryDTO categoryDTO = cacategoryService.findCategoryById(categoryId);
    return ResponseEntity.ok(categoryDTO);
  }

  @GetMapping("/get-all")
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    List<CategoryDTO> categoryDTO = cacategoryService.getAllCategory();
    return ResponseEntity.ok(categoryDTO);
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId,
                                             @RequestHeader("Authorization") String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    cacategoryService.deleteCategory(categoryId, user);
    return ResponseEntity.noContent().build();
  }

}
