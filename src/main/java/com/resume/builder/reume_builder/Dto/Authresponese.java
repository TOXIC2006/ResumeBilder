package com.resume.builder.reume_builder.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authresponese {
//    @JsonProperty("_id")// Map the MongoDB "_id" field to "id" in the DTO
    private  String id;
    private  String name;
    private String email;
    private  String profileurl;
    private  String subscpriptionPlan;
    private  boolean  emailVerified;
    private String token;
   private LocalDateTime createdAt;
   private  LocalDateTime updatedAt;


}
