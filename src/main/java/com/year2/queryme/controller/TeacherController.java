package com.year2.queryme.controller;

import com.year2.queryme.model.Teacher;
import com.year2.queryme.repository.TeacherRepository;
import com.year2.queryme.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher register(@RequestBody Map<String, String> data) {
        return teacherService.registerTeacher(
                data.get("email"),
                data.get("password"),
                data.get("fullName"),
                data.get("department")
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public Teacher update(@PathVariable String id, @RequestBody Map<String, String> data) {
        return teacherService.updateProfile(id, data);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public Page<Teacher> getAll(Pageable pageable) {
        if (org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
            String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
            return teacherRepository.findByUserEmail(email)
                    .map(t -> (Page<Teacher>) new org.springframework.data.domain.PageImpl<>(java.util.List.of(t), pageable, 1))
                    .orElse(Page.empty());
        }
        return teacherRepository.findAll(pageable);
    }
}
