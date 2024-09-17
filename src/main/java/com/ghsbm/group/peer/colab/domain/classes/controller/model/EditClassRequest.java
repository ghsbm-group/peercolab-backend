package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * Request used for editing a new class.
 *
 * <p>Encapsulates class configuration data.
 */
@Data
@Builder
public class EditClassRequest {
  @NotNull private Long classId;

  @NotNull
  @Size(min = 1, max = 50)
  private String name;
}
