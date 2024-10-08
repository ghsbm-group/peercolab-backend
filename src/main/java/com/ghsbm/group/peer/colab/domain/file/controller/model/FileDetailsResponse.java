package com.ghsbm.group.peer.colab.domain.file.controller.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDetailsResponse {
  private List<FileDetailsDTO> files;
}
