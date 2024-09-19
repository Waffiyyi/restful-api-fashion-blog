package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.CategoryDTO;
import com.waffiyyi.fashion.blog.entities.Category;

public interface CategoryService {
  CategoryDTO saveCategory(CategoryDTO categoryDTO);

  CategoryDTO findCategoryById(Long categoryId);

  void deleteCategory(Long categoryId);
}