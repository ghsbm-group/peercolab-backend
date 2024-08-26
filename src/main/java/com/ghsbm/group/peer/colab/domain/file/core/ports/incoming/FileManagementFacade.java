package com.ghsbm.group.peer.colab.domain.file.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.FileRepository;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.StorageService;
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
  public File saveFile(MultipartFile file, File fileInfo) {
    fileInfo.setName(file.getOriginalFilename());
    final var fileInformation = fileRepository.saveFile(fileInfo);
    storageService.store(file);

    return fileInformation;
  }
}
