package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import com.ghsbm.group.peer.colab.domain.chat.controller.model.CreateMessageResponse;
import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.FolderDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMessageBoardResponse {

  private FolderDTO folderDTO;
  private CreateMessageResponse createMessageResponse;
}
