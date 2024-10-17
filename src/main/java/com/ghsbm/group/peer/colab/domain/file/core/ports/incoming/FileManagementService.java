package com.ghsbm.group.peer.colab.domain.file.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import com.ghsbm.group.peer.colab.domain.file.core.model.FileUserDetails;
import com.ghsbm.group.peer.colab.domain.file.core.model.FileInfo;
import java.util.List;

import com.ghsbm.group.peer.colab.domain.file.core.model.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface FileManagementService {

  @Transactional
  FileInfo saveFile(MultipartFile file, long folderId, String descritpion);

  List<FileUserDetails> listFiles(Long folderId);

  File download(Long fileId);

  void deleteFile(Long fileId);

  UserDetails retrieveUserDetails(Long userId);
}
