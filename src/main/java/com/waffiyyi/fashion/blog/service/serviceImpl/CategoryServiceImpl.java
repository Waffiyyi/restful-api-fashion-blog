package com.waffiyyi.fashion.blog.service.serviceImpl;

import com.waffiyyi.fashion.blog.DTOs.CategoryDTO;
import com.waffiyyi.fashion.blog.entities.Category;
import com.waffiyyi.fashion.blog.repositories.CategoryRepository;
import com.waffiyyi.fashion.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO){
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }
    @Override
    public CategoryDTO findCategoryById(Long categoryId){
        Category category = categoryRepository.getCategoryById(categoryId);
       return convertToDto(category);
    }

    @Override
    public void deleteCategory(Long categoryId){
        categoryRepository.deleteById(categoryId);
    }

    private CategoryDTO convertToDto(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
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
