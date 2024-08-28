package com.ghsbm.group.peer.colab.domain.file.controller.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadFileResponse {
    private Long id;
    private String name;
    private LocalDateTime fileDate;
    private Long folderId;
}
