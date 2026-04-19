package com.resume.builder.reume_builder.repository;

import com.resume.builder.reume_builder.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository  extends MongoRepository<User, String> {
   User findByEmail(String email);// fincber method
     boolean existsByEmail(String email);
     Optional<User> findByVericationToken(String token);
     boolean  existsById(String token);
}
