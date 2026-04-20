package com.resume.builder.reume_builder.repository;

import com.resume.builder.reume_builder.document.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface resumeRepository  extends MongoRepository<Resume, String> {
   List<Resume> findByUserIdOrderByUpdatedAtDesc(String Id);
    Resume findByUserIdAndId(String userid , String id);
}
