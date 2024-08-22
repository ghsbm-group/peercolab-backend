package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.FolderInfoDTO;
import lombok.Data;

@Data
public class CreateMessageBoardRequest {
  private FolderInfoDTO folderInfoDTO;
  private String firstMessage;
}
