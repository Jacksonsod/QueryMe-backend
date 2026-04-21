package com.year2.queryme.model.dto;

import com.year2.queryme.model.enums.QuestionResultStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TeacherDashboardRowDto {
    private UUID studentId;
    private String studentName;
    private UUID sessionId;
    private UUID questionId;
    private String questionPrompt;
    private Integer score;
    private Integer maxScore;
    private QuestionResultStatus status;
    private String submittedQuery;
    private LocalDateTime submittedAt;
    private String teacherFeedback;
}
