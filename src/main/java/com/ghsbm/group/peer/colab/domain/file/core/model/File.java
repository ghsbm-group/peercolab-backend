package com.ghsbm.group.peer.colab.domain.file.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class File {
    private Long id;
    private String name;
    private String path;
    private Long folderId;
    private LocalDateTime fileDate;

}
