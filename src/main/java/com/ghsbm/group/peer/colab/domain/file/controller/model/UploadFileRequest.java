package com.ghsbm.group.peer.colab.domain.file.controller.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Data
@Builder
public class UploadFileRequest {
  private Long folderId;
  private String path;
  private String description;
}
