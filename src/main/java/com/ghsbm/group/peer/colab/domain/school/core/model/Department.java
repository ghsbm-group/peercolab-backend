package com.ghsbm.group.peer.colab.domain.school.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Department {
  private Long id;
  private String name;
  private Long facultyId;
}
