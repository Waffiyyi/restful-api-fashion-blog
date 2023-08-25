package com.example.week8taask.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Designs")
public class Design {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    @Column
    private String description;
    @Column
    private int likesCount;
   @ManyToOne
   @JoinColumn(name = "category_id")
   private Category category;
}
