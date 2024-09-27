package com.waffiyyi.fashion.blog.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String name;
    private String description;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long categoryCreatorId;
}
