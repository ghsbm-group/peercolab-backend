package com.ghsbm.group.peer.colab.domain.school.controller.model;

import lombok.Data;

@Data
public class ClassConfigurationDTO {
  private Long id;
  private String name;
  private Integer startYear;
  private Integer noOfStudyYears;
  private Integer noOfSemestersPerYear;
}
