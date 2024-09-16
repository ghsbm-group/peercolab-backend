package com.ghsbm.group.peer.colab.domain.file.persistance;

import com.ghsbm.group.peer.colab.domain.file.core.model.FileInfo;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.FileRepository;
import com.ghsbm.group.peer.colab.domain.file.persistance.model.FileEntitiesMapper;
import com.ghsbm.group.peer.colab.domain.file.persistance.model.FileEntity;
import com.ghsbm.group.peer.colab.domain.file.persistance.repository.FilePsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.UserRepository;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Setter
public class FileRepositoryAdapter implements FileRepository {
  private FilePsqlDbRepository filePsqlDbRepository;
  private UserRepository userRepository;
  private FileEntitiesMapper fileEntitiesMapper;

  @Autowired
  public FileRepositoryAdapter(
      FilePsqlDbRepository filePsqlDbRepository,
      FileEntitiesMapper fileEntitiesMapper,
      UserRepository userRepository) {
    this.fileEntitiesMapper = fileEntitiesMapper;
    this.filePsqlDbRepository = filePsqlDbRepository;
    this.userRepository = userRepository;
  }

  // change to save file
  @Override
  public FileInfo saveFile(FileInfo fileInfo) {
    String userLogin = SecurityUtils.getCurrentUserLogin().get();
    UserEntity userEntity =
        userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        "User with username " + userLogin + " does not exist"));
    FileEntity fileEntity =
        FileEntity.builder()
            .name(fileInfo.getName())
            .folderId(fileInfo.getFolderId())
            .user(userEntity.getId())
            .path(fileInfo.getPath())
            .fileDate(ZonedDateTime.now(ZoneId.of("Europe/Bucharest")))
            .build();
    FileEntity savedFile = filePsqlDbRepository.save(fileEntity);
    return fileEntitiesMapper.fileFromEntity(savedFile);
  }

  @Override
  public List<FileInfo> listFiles(long folderId) {
    List<FileEntity> byFolderId = filePsqlDbRepository.findAllByFolderId(folderId);
    return fileEntitiesMapper.map(byFolderId);
  }

  @Override
  public FileInfo getById(Long fileId) {
    return fileEntitiesMapper.fileFromEntity(filePsqlDbRepository.getReferenceById(fileId));
  }
}
