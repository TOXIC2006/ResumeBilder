package com.resume.builder.reume_builder.Security;

import com.resume.builder.reume_builder.document.User;
import com.resume.builder.reume_builder.repository.UserRepository;
import com.resume.builder.reume_builder.util.jwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenitacationfilter  extends OncePerRequestFilter {

      @Autowired
     private jwtUtils jwtUtils;

       @Autowired
       private   UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String authHeader=request.getHeader("Authorization");
            String token=null;
            String userid=null;

             if(authHeader!=null && authHeader.startsWith("Bearer")){
               token=authHeader.substring(7);
                try{
                  userid=   jwtUtils.getuserIdfromToken(token);
                       // valid token and user exists
                    // you can set authentication in security context here if needed

                }catch (Exception e){
                    logger.error(" token is not valid {} "+token);

                }
                 if(userid!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                     try{
                         if( jwtUtils.validateToken(token) && !jwtUtils.istokenEpire(token)){
                             User user= userRepository.findById(userid).orElseThrow(()-> new RuntimeException("user not found with id: "));
                             UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>() );
                             authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));


                                SecurityContextHolder.getContext().setAuthentication(authToken);
                          }
                     }catch (Exception e){
                            logger.error(" token is not valid {} "+token);
                            throw new RuntimeException("token is not valid");
                     }

                     }
                 }
                 filterChain.doFilter( request, response);
                 
    }
}
