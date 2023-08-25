package com.example.week8taask.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

@Entity
@Data
@Table(name = "design_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String description;
}
