package com.example.week8taask.Repositories;

import com.example.week8taask.Entities.Design;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignRepo extends JpaRepository<Design, Long> {
    Design findByName(String name);
    Design getDesignEntityById(Long designId);
}
