package com.ghsbm.group.peer.colab.domain.classes.controller.model.dto;

import lombok.Data;

/** Encapsulates class data. */
@Data
public class ClassDTO {
  private Long id;
  private String name;
  private Integer startYear;
  private Integer noOfStudyYears;
  private Integer noOfSemestersPerYear;
}
