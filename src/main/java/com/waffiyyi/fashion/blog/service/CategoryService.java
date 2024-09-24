package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.CategoryDTO;
import com.waffiyyi.fashion.blog.entities.User;

import java.util.List;

public interface CategoryService {
  CategoryDTO saveCategory(CategoryDTO categoryDTO, User user);

  CategoryDTO findCategoryById(Long categoryId);
  List<CategoryDTO> getAllCategory();

  void deleteCategory(Long categoryId, User user);
}