package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.FolderDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateFolderResponse {
  FolderDTO folderDTO;
}
