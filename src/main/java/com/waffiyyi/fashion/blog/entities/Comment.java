package com.waffiyyi.fashion.blog.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "design_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "design_id")
    private Design design;
    @ManyToOne
    private User commenter;
}
