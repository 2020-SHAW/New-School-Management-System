package com.admin.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.admin.school.entity.Teacher;
import com.admin.school.repository.TeacherRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    private static final String UPLOAD_DIR = "uploads/";

    // Handle file upload
    @PostMapping("/{id}/upload")
    public String uploadFile(@PathVariable String id,
                             @RequestParam("file") MultipartFile file) throws IOException {

        // Create upload directory if it doesn't exist
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Save the file on disk
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.write(path, file.getBytes());

        // Get teacher and update file path
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        if (file.getContentType().startsWith("image")) {
            teacher.setPhotoPath(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString());
        } else {
            teacher.setResumePath(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString());
        }
        
        // Save the teacher with updated file path
        teacherRepository.save(teacher);

        return "File uploaded successfully!";
    }
}
