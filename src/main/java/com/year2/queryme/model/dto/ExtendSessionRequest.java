package com.year2.queryme.model.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class ExtendSessionRequest {
    @NotNull(message = "Extra hours cannot be null")
    @Min(value = 1, message = "Extra hours must be at least 1")
    private Integer extraHours;
}
