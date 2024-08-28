package com.ghsbm.group.peer.colab.domain.file.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.FileRepository;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.StorageService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileManagementFacade implements FileManagementService {

  private final FileRepository fileRepository;
  private final StorageService storageService;

  public FileManagementFacade(FileRepository fileRepository, StorageService storageService) {
    this.fileRepository = fileRepository;
    this.storageService = storageService;
  }

  @Override
  public File saveFile(MultipartFile file, long folderId) {
    final var key = buildKey(folderId, file.getOriginalFilename());
    final var fileInformation =
        fileRepository.saveFile(
            File.builder()
                .name(file.getOriginalFilename())
                .folderId(folderId)
                .path(key)
                .fileDate(LocalDateTime.now())
                .build());
    storageService.store(file, key);

    return fileInformation;
  }

  private String buildKey(long folderId, String originalFilename) {
    return "folder_id" + folderId + "/" + originalFilename;
  }
}
