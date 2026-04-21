package com.year2.queryme.model.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class FeedbackRequest {
    @NotBlank(message = "Feedback cannot be empty")
    private String feedback;
}
