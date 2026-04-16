package com.resume.builder.reume_builder.document;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private  String profileurl;
    private  String  subscpriptionPlan= "free";
    private boolean emailverifed= false;
    private  String vericationToken;
    private LocalDateTime verficationTokenExpiry;

    @CreatedDate
    private  LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
