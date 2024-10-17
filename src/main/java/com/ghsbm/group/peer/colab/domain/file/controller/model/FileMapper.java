package com.ghsbm.group.peer.colab.domain.file.controller.model;

import com.ghsbm.group.peer.colab.domain.file.core.model.FileUserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FileMapper {

  public List<FileUserInfo> mapList(List<FileUserDetails> fileUserDetails) {
    if (fileUserDetails == null) {
      return Collections.emptyList();
    }

    List<FileUserInfo> list = new ArrayList<FileUserInfo>(fileUserDetails.size());
    for (FileUserDetails file : fileUserDetails) {
      list.add(map(file));
    }

    return list;
  }

  public FileUserInfo map(FileUserDetails fileUserDetails) {
    if (fileUserDetails == null) {
      return null;
    }
    UserDTO userDTO = new UserDTO();
    FileDetailsDTO fileDetailsDTO = new FileDetailsDTO();
    userDTO.setLogin(fileUserDetails.getUserDetails().getLogin());
    userDTO.setFirstName(fileUserDetails.getUserDetails().getFirstName());
    userDTO.setLastName(fileUserDetails.getUserDetails().getLastName());

    fileDetailsDTO.setId(fileUserDetails.getFileInfo().getId());
    fileDetailsDTO.setName(fileUserDetails.getFileInfo().getName());
    fileDetailsDTO.setDescription(fileUserDetails.getFileInfo().getDescription());
    fileDetailsDTO.setFileDate(fileUserDetails.getFileInfo().getFileDate());
    fileDetailsDTO.setFolderId(fileUserDetails.getFileInfo().getFolderId());
    fileDetailsDTO.setIsFileUploadedByLoggedInUser(
        fileUserDetails.getFileInfo().getIsFileUploadedByLoggedInUser());

    return FileUserInfo.builder().userDTO(userDTO).fileDetailsDTO(fileDetailsDTO).build();
  }
}
