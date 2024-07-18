package com.ghsbm.group.peer.colab.domain.school.core.model;

import lombok.Builder;
import lombok.Data;

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
