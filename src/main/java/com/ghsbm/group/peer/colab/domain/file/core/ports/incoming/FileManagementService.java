package com.ghsbm.group.peer.colab.domain.file.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface FileManagementService {

  @Transactional
  File saveFile(MultipartFile file, long folderId);
}
