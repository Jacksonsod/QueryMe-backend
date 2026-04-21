package com.year2.queryme.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "exam_attempt_overrides", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"exam_id", "student_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamAttemptOverride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "exam_id", nullable = false)
    private String examId;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "additional_attempts", nullable = false)
    private Integer additionalAttempts;
}
