package com.waffiyyi.fashion.blog.service.serviceImpl;

import com.waffiyyi.fashion.blog.DTOs.CategoryDTO;
import com.waffiyyi.fashion.blog.entities.Category;
import com.waffiyyi.fashion.blog.entities.Design;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.exception.ResourceNotFoundException;
import com.waffiyyi.fashion.blog.exception.UnAuthorizedException;
import com.waffiyyi.fashion.blog.repositories.CategoryRepository;
import com.waffiyyi.fashion.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  @Override
  public CategoryDTO saveCategory(CategoryDTO categoryDTO, User user) {
    Category category = convertToEntity(categoryDTO, user);
    Category savedCategory = categoryRepository.save(category);
    return convertToDto(savedCategory);
  }

  @Override
  public CategoryDTO findCategoryById(Long categoryId) {
    Category category =
       categoryRepository.findById(categoryId).orElseThrow(
          () -> new ResourceNotFoundException("Category not found",
                                              HttpStatus.NOT_FOUND));
    return convertToDto(category);
  }

  @Override
  public List<CategoryDTO> getAllCategory() {
    List<Category> categories = categoryRepository.findAll();
    List<CategoryDTO> categoryDTOList = new ArrayList<>();
    for(Category category: categories){
      categoryDTOList.add(convertToDto(category));
    }
    return categoryDTOList;
  }

  @Override
  public void deleteCategory(Long categoryId, User user) {
    Category category =
       categoryRepository.findById(categoryId).orElseThrow(
          () -> new ResourceNotFoundException("Category not found",
                                              HttpStatus.NOT_FOUND));
    if (!Objects.equals(user.getId(), category.getCreator().getId())) {
      throw new UnAuthorizedException("You are not authorized to delete a category you " +
                                         "don't own",
                                      HttpStatus.UNAUTHORIZED);
    }
    categoryRepository.deleteById(categoryId);
  }

  private CategoryDTO convertToDto(Category category) {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setName(category.getName());
    categoryDTO.setDescription(categoryDTO.getDescription());
    categoryDTO.setCategoryCreatorId(category.getCreator().getId());
    return categoryDTO;
  }

  private Category convertToEntity(CategoryDTO categoryDTO, User user) {
    Category category = new Category();
    category.setName(categoryDTO.getName());
    category.setDescription(categoryDTO.getDescription());
    category.setCreator(user);
    return category;
  }
}
