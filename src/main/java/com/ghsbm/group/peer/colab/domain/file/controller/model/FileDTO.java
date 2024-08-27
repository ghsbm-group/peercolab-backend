package com.ghsbm.group.peer.colab.domain.file.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDTO {
    private Long folderId;
    private String path;
}
