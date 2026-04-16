package com.resume.builder.reume_builder.service;

import com.resume.builder.reume_builder.Dto.Authresponese;
import com.resume.builder.reume_builder.Dto.RegisterRequest;
import com.resume.builder.reume_builder.document.User;
import com.resume.builder.reume_builder.exepction.Resourceexpection;
import com.resume.builder.reume_builder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final emailService emailService;

    @Value("${app.base.url:http://localhost:8080}")
     private  String appurl;

    public Authresponese register(RegisterRequest registerRequest) {
        log.info("inside register method of authservice");
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            log.error("email already exists");
            throw new Resourceexpection("message: email already exists");
        }
        User newUser =usertodocument(registerRequest);

        User savedUser = userRepository.save(newUser);
//             todo send verification email to user email with the verifiacation token

         sendVerificationEmail(newUser);
        return toResponse( newUser);
    }

    private void sendVerificationEmail(User newUser) {
         try{
             String link=appurl+"/api/auth/verify-email?token="+newUser.getVericationToken();
             log.info("verification link: and inside the  send vefication {}",link);
             String html="<div style=\"font-family: Arial, sans-serif;text-align:center;padding:20px;\">" +
                         "<h2>Welcome to Resume Builder!</h2>" +
                         "<p>Thank you for registering. Please click the button below to verify your email address:</p>" +
                         "<p><a href=\"" + link + "\" style=\"display:inline-block;padding:12px 24px;color:#fff;background:#007bff;text-decoration:none;border-radius:4px;\">Verify Email</a></p>" +
                         "<p>Alternatively, you can copy and paste this link into your browser:</p>" +
                         "<p><a href=\"" + link + "\">" + link + "</a></p>" +
                         "</div>";

             emailService.sendHtmlEmail(newUser.getEmail(), "Verify Your Email Address", html);
         }catch (Exception e){
             log.error("failed to send verification email",e);
              throw  new RuntimeException(" failed to send verification email"+e.getMessage());
         }
    }


    private  Authresponese   toResponse(User newUser){
        log.info("inside to response method of authservice {}",newUser);
        return Authresponese.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .profileurl(newUser.getProfileurl())
                .subscpriptionPlan(newUser.getSubscpriptionPlan())
                .emailVerified(newUser.isEmailverifed())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
}
         private  User usertodocument(RegisterRequest
                                              registerRequest){
           return User.builder()
                   .name(registerRequest.getName())
                   .email(registerRequest.getEmail())
                   .password(registerRequest.getPassword())
                   .profileurl(registerRequest.getProfileurl())
                   .subscpriptionPlan("free")
                   .emailverifed(false)
                   .vericationToken(UUID.randomUUID().toString())
                   .verficationTokenExpiry(LocalDateTime.now().plusHours(24))// the toekn expries  before the 24 hours
                   .build();
         }
          public void verifyemail(String token){
             log.info("inside verify email method of authservice with token: {}",token);
             User usertoken = userRepository.findByVericationToken(token)
                        .orElseThrow(() -> new Resourceexpection("invalid verification token"));
             if(usertoken.getVerficationTokenExpiry().isBefore(LocalDateTime.now()) && !usertoken.isEmailverifed()){
                 throw new Resourceexpection("verification token has expired");
             }
             usertoken.setEmailverifed(true);
             usertoken.setVericationToken(null);
             usertoken.setVerficationTokenExpiry(null);
             userRepository.save(usertoken);
          }
}