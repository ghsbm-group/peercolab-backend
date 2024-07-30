package com.ghsbm.group.peer.colab.domain.school.controller.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUniversityRequest {
  @NotNull private Long cityId;

  @NotNull
  @Size(min = 1, max = 100)
  private String name;
}
