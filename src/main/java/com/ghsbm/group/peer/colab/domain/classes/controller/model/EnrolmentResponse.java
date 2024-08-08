package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Response returned on the enrol user endpoint.
 *
 * <p>Encapsulates class data.
 */
@Data
@Builder
public class EnrolmentResponse {
  private Long classConfigurationId;
  private List<FolderDTO> folders;
}
