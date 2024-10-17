package com.ghsbm.group.peer.colab.domain.file.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import com.ghsbm.group.peer.colab.domain.file.core.model.FileUserDetails;
import com.ghsbm.group.peer.colab.domain.file.core.model.FileInfo;
import com.ghsbm.group.peer.colab.domain.file.core.model.UserDetails;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.FileRepository;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.StorageService;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
  public FileInfo saveFile(MultipartFile file, long folderId, String description) {
    final var key = buildKey(folderId, file.getOriginalFilename());
    final var fileInformation =
        fileRepository.saveFile(
            FileInfo.builder()
                .name(file.getOriginalFilename())
                .folderId(folderId)
                .path(key)
                .fileDate(ZonedDateTime.now())
                .description(description)
                .build());
    storageService.store(file, key);

    return fileInformation;
  }

  @Override
  public List<FileUserDetails> listFiles(Long folderId) {
    List<FileInfo> files = isUploadByLoggedInUser(fileRepository.listFiles(folderId));

    List<FileUserDetails> fileDetails = new ArrayList<>();

    for (FileInfo file : files) {
      UserDetails userDetails = retrieveUserDetails(file.getUser());
      fileDetails.add(FileUserDetails.builder().fileInfo(file).userDetails(userDetails).build());
    }

    return fileDetails;
  }

  @Override
  public File download(Long fileId) {
    FileInfo fileInfo = fileRepository.getById(fileId);
    byte[] fileContent = storageService.load(fileInfo.getName(), fileInfo.getPath());
    return File.builder().file(fileContent).fileInfo(fileInfo).build();
  }

  @Override
  public void deleteFile(Long fileId) {
    fileRepository.deleteFile(fileId);
  }

  @Override
  public UserDetails retrieveUserDetails(Long userId) {
    User user =
        userManagementService
            .findOneById(userId)
            .orElseThrow(() -> new IllegalStateException("User with id " + userId + " not found"));
    return UserDetails.builder()
        .login(user.getLogin())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .build();
  }

  private String buildKey(long folderId, String originalFilename) {
    return "folder_id_" + folderId + "/" + originalFilename;
  }

  protected List<FileInfo> isUploadByLoggedInUser(List<FileInfo> files) {
    String username =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException("User not logged in"));
    User user =
        userManagementService
            .getUserWithAuthoritiesByLogin(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    return files.stream()
        .peek(
            fileInfo ->
                fileInfo.setIsFileUploadedByLoggedInUser(fileInfo.getUser() == user.getId()))
        .collect(Collectors.toList());
  }
}
