package com.resume.builder.reume_builder.controler;

import com.resume.builder.reume_builder.Dto.CreateResumeDto;
import com.resume.builder.reume_builder.document.Resume;
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

import static com.resume.builder.reume_builder.util.Appconstrains.Resume_Controller;

@RestController
@RequestMapping(Resume_Controller )
@Slf4j
@RequiredArgsConstructor
public class ResumeController {


    private final ResumeService resumeService;

    @PostMapping("/create")
    public ResponseEntity<?> CreateResume(@Valid @RequestBody CreateResumeDto createResumeDto,
                                           Authentication  authentication){
       Resume newresume=  resumeService.CreateReusme(createResumeDto, authentication.getPrincipal());
        return ResponseEntity.status(HttpStatus.CREATED).body(newresume);
    }

    @GetMapping()
    public ResponseEntity<?> getUserResumes(){
        return ResponseEntity.ok("user resumes");
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getResumeById(@PathVariable String id){
        return ResponseEntity.ok("resume with id: "+id);
    }

    @PutMapping("upadate/{id}")
    public  ResponseEntity<?> updateResume(@Valid @PathVariable String id, @RequestBody Resume upadtedData){
          return null;
    }
    @PutMapping("/update-Image/{id}")
    public  ResponseEntity<?> upadateresume(@Valid @PathVariable String id,
                                            @RequestPart(value = "thumbnail", required = true)MultipartFile thumbnail,
                                            @RequestPart(value = "profileImage", required = false)MultipartFile  image,
                                             HttpServletRequest request){
 return null;
    }

    @DeleteMapping("Delete-resume/{id}")
    public ResponseEntity<?> deleteResume (@Valid @PathVariable String id ){
            return null;
    }


}


