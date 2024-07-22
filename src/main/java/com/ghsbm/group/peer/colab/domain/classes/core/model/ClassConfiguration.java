package com.ghsbm.group.peer.colab.domain.classes.core.model;

import lombok.Builder;
import lombok.Data;

/** Encapsulates class configuration data. */
@Data
@Builder
public class ClassConfiguration {
  private Long id;
  private String name;
  private Integer startYear;
  private Integer noOfStudyYears;
  private Integer noOfSemestersPerYear;
  private Long departmentId;
}
