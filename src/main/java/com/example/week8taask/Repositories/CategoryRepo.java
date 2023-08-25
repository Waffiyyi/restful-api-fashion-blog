package com.example.week8taask.Repositories;

import com.example.week8taask.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
   Category getCategoryById(Long id);
//    void deleteById(Category categoryId);
}
