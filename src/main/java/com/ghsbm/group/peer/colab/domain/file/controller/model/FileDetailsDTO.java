package com.ghsbm.group.peer.colab.domain.file.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDetailsDTO {
  private Long id;
  private String name;
  private ZonedDateTime fileDate;
  private Long folderId;
  private Boolean isFileUploadedByLoggedInUser;
  private String description;
}
