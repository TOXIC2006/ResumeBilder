package com.resume.builder.reume_builder.controler;

import com.resume.builder.reume_builder.Dto.Authresponese;
import com.resume.builder.reume_builder.Dto.LoginRequest;
import com.resume.builder.reume_builder.Dto.RegisterRequest;
import com.resume.builder.reume_builder.service.AuthService;
import com.resume.builder.reume_builder.service.FileUploadservice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static com.resume.builder.reume_builder.util.Appconstrains.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(Auth_contoller)
public class Authcontroller {

  private final AuthService authService;
  private final FileUploadservice fileUploadservice;


  @PostMapping(Register_Emial)
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {

    Authresponese authresponese = authService.register(registerRequest);
    log.info("user registered successfully with email: {}", registerRequest.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(authresponese);

  }

  @GetMapping(Verify_Email)
  public ResponseEntity<?> verifyEmail(@RequestParam String token) {
    // todo implement email verification logic
    log.info("inside verify email endpoint with token: {}", token);
    authService.verifyemail(token);
    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "email verified successfully"));
  }

  @PostMapping(Upload_image)
  public ResponseEntity<?> uploadProfileImage(@RequestParam("file") MultipartFile file) throws IOException {
    Map<String, String> image = fileUploadservice.uploadsinfgalimage(file);
    return ResponseEntity.ok(image);
  }

  @PostMapping(Login)
  public ResponseEntity<?> login( @Valid @RequestBody LoginRequest loginRequest) {
    Authresponese authresponese = authService.login(loginRequest);
    log.info("user logged in successfully with email: {}", loginRequest.getEmail());
    return ResponseEntity.ok(authresponese);

  }
   @PostMapping( resendEmail)
   public ResponseEntity<?> resendemialverifaction(@RequestBody  Map< String, String> request) {
     String email = request.get("email");
      if( email == null || email.isEmpty()) {
        log.error("email is required to resend verification email");
        return ResponseEntity.badRequest().body(Map.of("error", "email is required"));
      }
       authService.resendVerificationEmail(email);
     log.info("verification email resent successfully to email: {}", email);
     return ResponseEntity.ok(Map.of("message", "verification email resent successfully"));


   }
    @GetMapping(Getprofile)
    public ResponseEntity<?> getprofile(Authentication authentication){
//     step 1 get the princoiple object
       Object  princpleobject= authentication.getPrincipal();
       Authresponese authresponese = authService.getprofile(princpleobject);
       log.info("profile retrieved successfully for user: {}", authresponese.getEmail());
       return ResponseEntity.ok(authresponese);

    }


}
