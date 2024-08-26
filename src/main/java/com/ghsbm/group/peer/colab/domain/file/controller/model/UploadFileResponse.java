package com.ghsbm.group.peer.colab.domain.file.controller.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UploadFileResponse {
    private Long id;
    private String name;
    private LocalDateTime fileDate;
    private String path;
    private Long folderId;
}
