package com.resume.builder.reume_builder.service;

import com.resume.builder.reume_builder.Dto.Authresponese;
import com.resume.builder.reume_builder.Dto.CreateResumeDto;
import com.resume.builder.reume_builder.document.Resume;
import com.resume.builder.reume_builder.repository.resumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ResumeService {
     @Autowired
     private resumeRepository  resumeRepository;

     @Autowired
     private  AuthService authService;


    public Resume CreateReusme(CreateResumeDto  request, Object PrincipleObject) {

         Resume newResume=  new Resume();

          Authresponese  response= authService.getprofile(PrincipleObject);
          newResume.setId( response.getId());
          newResume.setTitle( request.getTitle());

          setDefalutRsumeData(newResume);

           return resumeRepository.save(newResume);

    }

    private void setDefalutRsumeData(Resume newResume) {
         newResume.setProfileInfo( new Resume.ProfileInfo());
         newResume.setContactInfo( new  Resume.ContactInfo());
         newResume.setWorkExperience( new ArrayList<>());
        newResume.setEducation( new ArrayList<>());
        newResume.setSkills( new ArrayList<>());
        newResume.setProjects( new ArrayList<>());
        newResume.setLanguages( new ArrayList<>());
        newResume.setInterests( new ArrayList<>());



    }
}
