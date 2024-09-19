package com.waffiyyi.fashion.blog.repositories;

import com.waffiyyi.fashion.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   Category getCategoryById(Long id);
//    void deleteById(Category categoryId);
}
