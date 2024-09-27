package com.waffiyyi.fashion.blog.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String text;
    private Long designId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long commenterId;
}
