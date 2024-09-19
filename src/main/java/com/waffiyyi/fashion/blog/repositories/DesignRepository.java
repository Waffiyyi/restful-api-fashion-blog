package com.waffiyyi.fashion.blog.repositories;

import com.waffiyyi.fashion.blog.entities.Design;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignRepository extends JpaRepository<Design, Long> {
    Design findByName(String name);
    Design getDesignEntityById(Long designId);
}
