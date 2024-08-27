package com.ghsbm.group.peer.colab.domain.file.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileManagementService {

  File saveFile(MultipartFile file, File fileInfo);
}
