package com.ghsbm.group.peer.colab.domain.classes.controller.model;

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
  private Long departmentId;
  private String name;
  private Integer startYear;
  private Integer noOfStudyYears;
  private Integer noOfSemestersPerYear;
}
