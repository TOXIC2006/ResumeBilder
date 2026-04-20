package com.resume.builder.reume_builder.controler;

import com.resume.builder.reume_builder.Dto.Authresponese;
import com.resume.builder.reume_builder.Dto.CreateResumeDto;
import com.resume.builder.reume_builder.document.Resume;
import com.resume.builder.reume_builder.service.AuthService;
import com.resume.builder.reume_builder.service.ResumeService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.resume.builder.reume_builder.util.Appconstrains.Resume_Controller;

@RestController
@RequestMapping(Resume_Controller)
@Slf4j
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    private final AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<?> CreateResume(@Valid @RequestBody CreateResumeDto createResumeDto,
            Authentication authentication) {
        Resume newresume = resumeService.CreateReusme(createResumeDto, authentication.getPrincipal());
        return ResponseEntity.status(HttpStatus.CREATED).body(newresume);
    }

    @GetMapping("/getUserResume")
    public ResponseEntity<?> getUserResume(Authentication authentication) {
        List<Resume> response = resumeService.getuserResume(authentication.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResumeById(@PathVariable String id,
            Authentication authentication) {

        Resume resume = resumeService.findByUserID(id, authentication.getPrincipal());
        return ResponseEntity.ok(resume);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateResume(@Valid @PathVariable String id,
            @RequestBody Resume upadtedData,
            Authentication authentication) {
        Resume updatedresume = resumeService.updateResume(id, upadtedData, authentication.getPrincipal());
        return ResponseEntity.ok(updatedresume);
    }

    @PutMapping("/update-Image/{id}")
    public ResponseEntity<?> updateResumeImage(@Valid @PathVariable String id,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestPart(value = "profileImage", required = false) MultipartFile image,
            Authentication authentication) {
        Resume updatedResume = resumeService.updateResumeImage(id, thumbnail, image, authentication.getPrincipal());
        return ResponseEntity.ok(updatedResume);
    }

    @DeleteMapping("/Delete-resume/{id}")
    public ResponseEntity<?> deleteResume(@Valid @PathVariable String id, Authentication authentication) {
        resumeService.deleteResume(id, authentication.getPrincipal());

        return ResponseEntity.noContent().build();
        // one can weite the messge or
    }

}
