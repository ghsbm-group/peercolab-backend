package com.ghsbm.group.peer.colab.domain.file.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import com.ghsbm.group.peer.colab.domain.file.core.model.FileInfo;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.FileRepository;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.StorageService;
import java.time.ZonedDateTime;
import java.util.List;
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
  public FileInfo saveFile(MultipartFile file, long folderId) {
    final var key = buildKey(folderId, file.getOriginalFilename());
    final var fileInformation =
        fileRepository.saveFile(
            FileInfo.builder()
                .name(file.getOriginalFilename())
                .folderId(folderId)
                .path(key)
                .fileDate(ZonedDateTime.now())
                .build());
    storageService.store(file, key);

    return fileInformation;
  }

  @Override
  public List<FileInfo> listFiles(Long folderId) {
    return fileRepository.listFiles(folderId);
  }

  @Override
  public File download(Long fileId) {
    FileInfo fileInfo = fileRepository.getById(fileId);
    byte[] fileContent = storageService.load(fileInfo.getName(), fileInfo.getPath());
    return File.builder().file(fileContent).fileInfo(fileInfo).build();
  }

  private String buildKey(long folderId, String originalFilename) {
    return "folder_id_" + folderId + "/" + originalFilename;
  }
}
