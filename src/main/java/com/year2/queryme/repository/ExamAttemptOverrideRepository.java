package com.year2.queryme.repository;

import com.year2.queryme.model.ExamAttemptOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamAttemptOverrideRepository extends JpaRepository<ExamAttemptOverride, UUID> {
    Optional<ExamAttemptOverride> findByExamIdAndStudentId(String examId, String studentId);
}
