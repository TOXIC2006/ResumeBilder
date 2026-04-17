package com.resume.builder.reume_builder.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileUploadservice {

    private final Cloudinary cloudinary;

    public Map<String, String> uploadsinfgalimage(MultipartFile file) throws IOException {
        Map<String, Object> imageuploadresult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "image"));
        return Map.of("image_url", imageuploadresult.get("secure_url").toString());
    }}
