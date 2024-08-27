package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.ClassDTO;
import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.FolderDTO;
import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.FolderPathDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RetrieveFolderResponse {
    List<FolderDTO> subfolders;
    List<FolderPathDTO> path;
    ClassDTO classDTO;
}
