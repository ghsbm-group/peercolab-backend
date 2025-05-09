package com.ghsbm.group.peer.colab.domain.file.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUserInfo {

  private FileDetailsDTO fileDetailsDTO;
  private UserDTO userDTO;
}
