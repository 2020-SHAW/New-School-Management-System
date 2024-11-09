// src/main/java/com/management/school/services/ExtracurricularActivityService.java
package com.admin.school.services;

import com.admin.school.repository.ExtracurricularActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class ExtracurricularActivityService {
    private final ExtracurricularActivityRepository repository;

    public ExtracurricularActivityService(ExtracurricularActivityRepository repository) {
        this.repository = repository;
    }
}
