package com.ghsbm.group.peer.colab.domain.file.controller;

import com.ghsbm.group.peer.colab.domain.file.controller.model.FileMapper;
import com.ghsbm.group.peer.colab.domain.file.controller.model.UploadFileResponse;
import com.ghsbm.group.peer.colab.domain.file.core.ports.incoming.FileManagementService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileManagementController {
  private final FileManagementService fileManagementService;
  private final FileMapper fileMapper;

  public FileManagementController(
      FileManagementService fileManagementService, FileMapper fileMapper) {
    this.fileManagementService = fileManagementService;
    this.fileMapper = fileMapper;
  }

  @RequestMapping(
      path = "/upload/{folderId}",
      method = RequestMethod.POST,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UploadFileResponse> handleFileUpload(
      @PathVariable Long folderId, @RequestParam("file") MultipartFile file) {

    final var fileInfo = fileManagementService.saveFile(file, folderId);

    return ResponseEntity.ok(
        UploadFileResponse.builder()
            .id(fileInfo.getId())
            .name(fileInfo.getName())
            .fileDate(fileInfo.getFileDate())
            .folderId(fileInfo.getFolderId())
            .build());
  }
}
