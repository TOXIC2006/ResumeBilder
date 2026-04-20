package com.resume.builder.reume_builder.service;

import com.resume.builder.reume_builder.Dto.Authresponese;
import com.resume.builder.reume_builder.Dto.CreateResumeDto;
import com.resume.builder.reume_builder.document.Resume;
import com.resume.builder.reume_builder.repository.resumeRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {
     @Autowired
     private resumeRepository  resumeRepository;

     @Autowired
     private  AuthService authService;

     @Autowired
     private FileUploadservice fileUploadService;
    public Resume CreateReusme(CreateResumeDto  request, Object PrincipleObject) {

         Resume newResume=  new Resume();

          Authresponese  response= authService.getprofile(PrincipleObject);
          newResume.setUserId( response.getId());
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

    public List<Resume> getuserResume(@Nullable Object principal) {
     Authresponese authresponese=   authService.getprofile(principal);
     List<Resume>  resumes=resumeRepository.findByUserIdOrderByUpdatedAtDesc(authresponese.getId());

     return resumes;

    }

    public Resume findByUserID(String id, Object principle) {

        Authresponese authresponese= authService.getprofile(principle);
          Resume resume= resumeRepository.findByUserIdAndId(authresponese.getId(), id);
         return resume;
    }

    public Resume updateResume(@Valid String reumeId, Resume upadtedData, @Nullable Object principal) {
      Authresponese response= authService.getprofile(principal);
       Resume existing= resumeRepository.findByUserIdAndId(response.getId(),reumeId);
       
       existing.setName(upadtedData.getName());
       existing.setTitle(upadtedData.getTitle());
       existing.setTemplate(upadtedData.getTemplate());
       existing.setProfileInfo(upadtedData.getProfileInfo());
       existing.setContactInfo(upadtedData.getContactInfo());
       existing.setWorkExperience(upadtedData.getWorkExperience());
       existing.setEducation(upadtedData.getEducation());
       existing.setSkills(upadtedData.getSkills());
       existing.setProjects(upadtedData.getProjects());
       existing.setCertifications(upadtedData.getCertifications());
       existing.setLanguages(upadtedData.getLanguages());
       existing.setInterests(upadtedData.getInterests());
       
       return resumeRepository.save(existing);
    }

    public Resume updateResumeImage(String resumeId, MultipartFile thumbnail, MultipartFile image, Object principal) {
        Authresponese response = authService.getprofile(principal);
        Resume existing = resumeRepository.findByUserIdAndId(response.getId(), resumeId);
        
        try {
            if (thumbnail != null && !thumbnail.isEmpty()) {
                java.util.Map<String, String> uploadResult = fileUploadService.uploadsinfgalimage(thumbnail);
                existing.setThumbnailUrl(uploadResult.get("image_url"));
            }
            if (image != null && !image.isEmpty()) {
                java.util.Map<String, String> uploadResult = fileUploadService.uploadsinfgalimage(image);
                if (existing.getProfileInfo() == null) {
                    existing.setProfileInfo(new Resume.ProfileInfo());
                }
                existing.getProfileInfo().setProfilePreviewUrl(uploadResult.get("image_url"));
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
        
        return resumeRepository.save(existing);
    }

    public void deleteResume(String resumeId, Object principal) {
        Authresponese response = authService.getprofile(principal);
        Resume existing = resumeRepository.findByUserIdAndId(response.getId(), resumeId);
        if (existing != null) {
            resumeRepository.delete(existing);
        }
    }
}
