package com.example.week8taask.Services;

import com.example.week8taask.DTOs.CategoryDTO;
import com.example.week8taask.Entities.Category;
import com.example.week8taask.Repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private  CategoryRepo categoryRepo;

    @Autowired
    private CategoryService(CategoryRepo categoryRepo){
      this.categoryRepo = categoryRepo;
    }
    public CategoryDTO saveCategory(CategoryDTO categoryDTO){
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepo.save(category);
        return convertToDto(savedCategory);
    }
    public CategoryDTO findCategoryById(Long categoryId){
        Category category = categoryRepo.getCategoryById(categoryId);
       return convertToDto(category);
    }

    public void deleteCategory(Long categoryId){
        categoryRepo.deleteById(categoryId);
    }

    private CategoryDTO convertToDto(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(categoryDTO.getDescription());
        return categoryDTO;
    }
    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
