package com.example.week8taask.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "design_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "design_id")
    private Design design;
}
