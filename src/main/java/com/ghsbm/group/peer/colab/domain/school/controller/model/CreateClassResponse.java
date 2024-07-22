package com.ghsbm.group.peer.colab.domain.school.controller.model;

import com.ghsbm.group.peer.colab.domain.school.core.model.Folder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateClassResponse {
  private Long classConfigurationId;
  List<FolderDTO> folders;
}
