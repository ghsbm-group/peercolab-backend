package com.ghsbm.group.peer.colab.domain.school.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class University {
  private Long id;
  private String name;
  private Long cityId;
}
