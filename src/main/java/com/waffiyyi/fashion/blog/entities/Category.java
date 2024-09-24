package com.waffiyyi.fashion.blog.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "design_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;


    private String name;
    private String description;
    @ManyToOne
    private User creator;
}
