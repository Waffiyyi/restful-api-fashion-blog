package com.waffiyyi.fashion.blog.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "designs")
public class Design {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
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
