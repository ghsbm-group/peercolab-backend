package com.ghsbm.group.peer.colab.domain.file.core.model;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfo {
  private Long id;
  private String name;
  private String path;
  private Long folderId;
  private ZonedDateTime fileDate;
}
