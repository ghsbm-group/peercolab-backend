package com.ghsbm.group.peer.colab.domain.school.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClassRequest {
  private Long departmentId;
  private String name;
  private Integer startYear;
  private Integer noOfStudyYears;
  private Integer noOfSemestersPerYear;
}
