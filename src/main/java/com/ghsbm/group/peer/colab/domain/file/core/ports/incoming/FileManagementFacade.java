package com.ghsbm.group.peer.colab.domain.file.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import com.ghsbm.group.peer.colab.domain.file.core.model.FileInfo;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.FileRepository;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.StorageService;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.UserManagementService;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileManagementFacade implements FileManagementService {

  private final FileRepository fileRepository;
  private final StorageService storageService;
  private final UserManagementService userManagementService;

  public FileManagementFacade(
      FileRepository fileRepository,
      StorageService storageService,
      UserManagementService userManagementService) {
    this.fileRepository = fileRepository;
    this.storageService = storageService;
    this.userManagementService = userManagementService;
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

    return uploadByLoggedInUser(fileRepository.listFiles(folderId));
  }

  @Override
  public File download(Long fileId) {
    FileInfo fileInfo = fileRepository.getById(fileId);
    byte[] fileContent = storageService.load(fileInfo.getName(), fileInfo.getPath());
    return File.builder().file(fileContent).fileInfo(fileInfo).build();
  }

  @Override
  public void deleetFile(Long fileId) {
    fileRepository.deleteFile(fileId);
  }

  private String buildKey(long folderId, String originalFilename) {
    return "folder_id_" + folderId + "/" + originalFilename;
  }

  protected List<FileInfo> uploadByLoggedInUser(List<FileInfo> files) {
    String username =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException("User not logged in"));
    User user =
        userManagementService
            .getUserWithAuthoritiesByLogin(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    List<FileInfo> list = new ArrayList<FileInfo>();
    for (FileInfo fileInfo : files) {
      if (fileInfo.getUser() == user.getId()) {
        fileInfo.setIsFileUploadedByLoggedInUser(Boolean.TRUE);
      } else {
        fileInfo.setIsFileUploadedByLoggedInUser(Boolean.FALSE);
      }
      list.add(fileInfo);
    }

    return list;
  }
}
