package com.ghsbm.group.peer.colab.domain.file.core.model;

import java.time.ZonedDateTime;

import com.ghsbm.group.peer.colab.domain.security.core.model.User;
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
  private Boolean isFileUploadedByLoggedInUser;
  private Long user;
}
