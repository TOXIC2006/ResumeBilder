package com.resume.builder.reume_builder.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Valid
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    @Size(min=2, max=50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")

    @Size(min=6, message = "Password must be at least 6 characters long")
    private String password;

    private  String profileurl;
}
