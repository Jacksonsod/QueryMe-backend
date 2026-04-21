package com.year2.queryme.controller;

import com.year2.queryme.model.Course;
import com.year2.queryme.model.Teacher;
import com.year2.queryme.repository.CourseRepository;
import com.year2.queryme.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private com.year2.queryme.service.CurrentUserService currentUserService;

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public Course create(@RequestBody java.util.Map<String, Object> data) {
        String name = (String) data.get("name");
        String code = (String) data.get("code");

        Teacher teacher;

        if (currentUserService.hasRole(com.year2.queryme.model.enums.UserTypes.TEACHER)) {
            // Teacher creates their own course — resolve from the authenticated user
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            teacher = teacherRepository.findByUserEmail(email)
                    .orElseThrow(() -> new RuntimeException("Teacher profile not found for authenticated user"));
        } else {
            // Admin must supply an explicit teacherId
            Object teacherIdObj = data.get("teacherId");
            if (teacherIdObj == null) {
                throw new RuntimeException("teacherId is required when creating a course as ADMIN");
            }
            Long teacherId = Long.parseLong(teacherIdObj.toString());
            teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));
        }

        Course course = Course.builder()
                .name(name)
                .code(code)
                .teacher(teacher)
                .build();

        return courseRepository.save(course);
    }

    @GetMapping
    public Page<Course> getAll(Pageable pageable) {
        if (currentUserService.hasRole(com.year2.queryme.model.enums.UserTypes.ADMIN)) {
            return courseRepository.findAll(pageable);
        }
        if (currentUserService.hasRole(com.year2.queryme.model.enums.UserTypes.TEACHER)) {
            String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
            return courseRepository.findByTeacherUserEmail(email, pageable);
        }
        // Students or others see nothing
        return Page.empty();
    }
}
