package com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.file.core.model.FileInfo;
import java.util.List;

// pt db
public interface FileRepository {
  FileInfo saveFile(FileInfo fileInfo);

  List<FileInfo> listFiles(long folderId);

  FileInfo getById(Long fileId);

  void deleteFile(Long fileId);
}
