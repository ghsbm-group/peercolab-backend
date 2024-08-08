package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import java.util.List;

import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.FolderDTO;
import lombok.Builder;
import lombok.Data;

/**
 * Response returned on the create class endpoint.
 *
 * <p>Encapsulates class data.
 */
@Data
@Builder
public class CreateClassResponse {
  private Long classConfigurationId;
  private List<FolderDTO> folders;
  private String enrolmentKey;
}
