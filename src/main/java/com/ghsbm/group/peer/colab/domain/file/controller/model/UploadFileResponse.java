package com.ghsbm.group.peer.colab.domain.file.controller.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class UploadFileResponse {
    private Long id;
    private String name;
    private ZonedDateTime fileDate;
    private String path;
    private Long folderId;
}
