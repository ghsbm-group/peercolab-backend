package com.ghsbm.group.peer.colab.domain.file.controller.model;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDTO {
  private Long id;
  private String name;
  private ZonedDateTime fileDate;
  private Long folderId;
  private String description;
}
