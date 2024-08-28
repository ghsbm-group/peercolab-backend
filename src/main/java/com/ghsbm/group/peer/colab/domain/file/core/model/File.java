package com.ghsbm.group.peer.colab.domain.file.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class File {
  private byte[] file;
  private FileInfo fileInfo;
}
