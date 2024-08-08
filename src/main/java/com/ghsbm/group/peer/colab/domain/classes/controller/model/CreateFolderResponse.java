package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.FolderDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFolderResponse {
  private FolderDTO folderDTO;
}
