package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * Request used for creating a new class.
 *
 * <p>Encapsulates class configuration data.
 */
@Data
@Builder
public class CreateClassRequest {
    @NotNull
    private Long departmentId;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @Min(1900)
    private Integer startYear;

    @NotNull
    @Min(value = 1)
    @Max(value = 6)
    private Integer noOfStudyYears;

    @NotNull
    @Min(value = 1)
    @Max(value = 6)
    private Integer noOfSemestersPerYear;
}
