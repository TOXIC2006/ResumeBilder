package com.resume.builder.reume_builder.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class CreateResumeDto {

    @NotBlank(message = "Title is required")
    private  String Title;
}
