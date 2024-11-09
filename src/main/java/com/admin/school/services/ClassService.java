package com.admin.school.services;

import com.admin.school.entity.Class;
import com.admin.school.repository.ClassRepository;
import com.admin.school.entity.CourseSubject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassService {

    private final ClassRepository repository;

    public ClassService(ClassRepository repository) {
        this.repository = repository;
    }

    public Optional<Class> get(Long id) {
        return repository.findById(id);
    }

    public Class save(Class entity) {
        return repository.save(entity);
    }

    public Class update(Class entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Class> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Class> list(Pageable pageable, Specification<Class> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }
}