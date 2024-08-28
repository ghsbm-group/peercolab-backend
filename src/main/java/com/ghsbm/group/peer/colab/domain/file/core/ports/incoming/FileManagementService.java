package com.ghsbm.group.peer.colab.domain.file.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import com.ghsbm.group.peer.colab.domain.file.core.model.FileInfo;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface FileManagementService {

  @Transactional
  FileInfo saveFile(MultipartFile file, long folderId);

  List<FileInfo> listFiles(Long folderId);

  File download(Long fileId);
}
