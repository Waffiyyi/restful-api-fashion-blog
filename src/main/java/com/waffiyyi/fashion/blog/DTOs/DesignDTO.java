package com.waffiyyi.fashion.blog.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class DesignDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String name;
    private List<String> images;
    private String description;
    private Long categoryId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long likesCount;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long ownerId;
}

