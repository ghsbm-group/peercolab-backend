package com.ghsbm.group.peer.colab.domain.file.controller;

import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.ADMIN;

import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.ClassManagementService;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.UserIsNotEnrolledInClassConfigurationException;
import com.ghsbm.group.peer.colab.domain.file.controller.model.FileDTO;
import com.ghsbm.group.peer.colab.domain.file.controller.model.FileMapper;
import com.ghsbm.group.peer.colab.domain.file.controller.model.FileDetailsResponse;
import com.ghsbm.group.peer.colab.domain.file.controller.model.UploadFileResponse;
import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import com.ghsbm.group.peer.colab.domain.file.core.ports.incoming.FileManagementService;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileManagementController {
  private final FileManagementService fileManagementService;
  private final ClassManagementService classManagementService;
  private final FileMapper fileMapper;

  public FileManagementController(
      FileManagementService fileManagementService,
      ClassManagementService classManagementService,
      FileMapper fileMapper) {
    this.fileManagementService = fileManagementService;
    this.classManagementService = classManagementService;
    this.fileMapper = fileMapper;
  }

  @RequestMapping(
      path = "/upload/{folderId}",
      method = RequestMethod.POST,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UploadFileResponse> handleFileUpload(
      @PathVariable Long folderId, @RequestParam("file") MultipartFile file) {
    if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(ADMIN)
        && !classManagementService.userIsEnrolled(folderId)) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }
    final var fileInfo = fileManagementService.saveFile(file, folderId);

    return ResponseEntity.ok(
        UploadFileResponse.builder()
            .file(
                FileDTO.builder()
                    .id(fileInfo.getId())
                    .name(fileInfo.getName())
                    .fileDate(fileInfo.getFileDate())
                    .folderId(fileInfo.getFolderId())
                    .build())
            .build());
  }

  @RequestMapping(path = "/list/{folderId}", method = RequestMethod.GET)
  public ResponseEntity<FileDetailsResponse> listFiles(@PathVariable Long folderId) {
    if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(ADMIN)
        && !classManagementService.userIsEnrolled(folderId)) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }
    final var fileInfos = fileManagementService.listFiles(folderId);
    return ResponseEntity.ok(
        FileDetailsResponse.builder().files(fileMapper.mapList(fileInfos)).build());
  }

  @GetMapping(value = "{fileId}")
  public byte[] download(@PathVariable("fileId") Long fileId, HttpServletResponse response) {
    File file = fileManagementService.download(fileId);
    if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(ADMIN)
        && !classManagementService.userIsEnrolled(file.getFileInfo().getFolderId())) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }
    response.setHeader(
        "Content-Disposition", "attachment; filename=" + file.getFileInfo().getName());
    response.setHeader("Access-Control-Expose-Headers", "*");
    return file.getFile();
  }

  @DeleteMapping("/delete")
  @ResponseStatus(HttpStatus.OK)
  public void deleteFolder(@NotNull final Long fileId) {
    fileManagementService.deleetFile(fileId);
  }
}
