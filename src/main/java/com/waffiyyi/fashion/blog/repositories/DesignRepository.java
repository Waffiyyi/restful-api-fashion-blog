package com.waffiyyi.fashion.blog.repositories;

import com.waffiyyi.fashion.blog.entities.Category;
import com.waffiyyi.fashion.blog.entities.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface DesignRepository extends JpaRepository<Design, Long> {
    List<Design> findTop10ByOrderByLikesCountDesc();
    @Query("SELECT d FROM Design d WHERE d.lastActivity >= :oneWeekAgo ORDER BY d.likesCount DESC")
    List<Design> findTrendingDesigns(@Param("oneWeekAgo") LocalDateTime oneWeekAgo);
    List<Design> findTop10ByCategoryInOrderByLikesCountDesc(Set<Category> categories);
    List<Design> findAllByCategory(Category category);

}
