package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.ClassDTO;
import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.ClassParentDetailsDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EnrolledClassesResponse {
    private Long id;
    private String name;
  private ClassDTO classInfo;
  private ClassParentDetailsDTO classParentDetails;
}
