package com.ghsbm.group.peer.colab.domain.school.controller.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDepartmentRequest {

  @NotNull private Long facultyId;

  @NotNull
  @Size(min = 1, max = 100)
  private String name;
}
