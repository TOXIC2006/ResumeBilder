package com.resume.builder.reume_builder.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collation = "resume")
public class Resume {
      @Id
      @JsonProperty("id")
     private String id;

      private  String userId;
      private String name;
      private String title;
      private String thumbnailUrl;

      private Template template;
        private ProfileInfo profileInfo;
        private ContactInfo contactInfo;
        private List<WorkExperience> workExperience;// in many conpmany work experience
        private List<Education> education;// multiple degree
        private List<Skills> skills;// mutiple skills
        private List<Projects> projects;
        private List<Certifications> certifications;
        private List<Languages> languages;
        private List<String> interests;

        @CreatedDate
        private LocalDateTime createdAt;
        @LastModifiedDate
        private LocalDateTime updatedAt;



      @Data
      @NoArgsConstructor
      @AllArgsConstructor
      @Builder
       public static  class Template{
                public static final String THEME="template1";
                private List<String> colorspalate;

       }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class ProfileInfo{
        public  String profilePreviewUrl;
        private  String fullName;
         private  String  summary;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class ContactInfo{
        public  String email;
        private  String phone;
        private  String  location;
        private  String Linkdein;
        private String github;
        private String Website;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class WorkExperience {
        private String company;
        private String role;
        private String startDate;
        private String endDate;
        private String description;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class Education {
        private String degree;
        private String institution;
        private String startDate;
        private String endDate;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class Skills {
         private String name;
         private String proficiency;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class Projects {
         private String title;
         private String description;
         private String gitlink;
         private String livelink;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class  Certifications {
        private String title;
        private String description;
       private String issuer;
         private String year;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class  Languages {
        private String Language;
        private Integer progress;

    }


}
