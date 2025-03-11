package com.manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttachmentDTO {
    private Long id; // For response purposes

    @NotBlank
    private String fileName;

    @NotBlank
    private String fileUrl;

}