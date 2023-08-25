package com.example.week8taask.DTOs;

import lombok.Data;

@Data
public class DesignDTO {

    private Long id;
    private String name;
    private String description;
    private Long category;
    private int likesCount;
}

