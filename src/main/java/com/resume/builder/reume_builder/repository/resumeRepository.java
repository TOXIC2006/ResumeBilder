package com.resume.builder.reume_builder.repository;

import com.resume.builder.reume_builder.document.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface resumeRepository  extends MongoRepository<Resume, String> {
}
