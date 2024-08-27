package com.ghsbm.group.peer.colab.domain.file.persistance;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.FileRepository;
import com.ghsbm.group.peer.colab.domain.file.persistance.model.FileEntitiesMapper;
import com.ghsbm.group.peer.colab.domain.file.persistance.model.FileEntity;
import com.ghsbm.group.peer.colab.domain.file.persistance.repository.FilePsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.UserRepository;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
  public File saveFile(File file) {
    String userLogin = SecurityUtils.getCurrentUserLogin().get();
    UserEntity userEntity =
        userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(
                () ->
                    new IllegalStateException("User with username" + userLogin + "does not exist"));
    FileEntity fileEntity =
        FileEntity.builder()
            .name(file.getName())
            .folderId(file.getFolderId())
            .user(userEntity.getId())
            .path(file.getPath())
            .fileDate(ZonedDateTime.now(ZoneId.of("Europe/Bucharest")))
            .build();
    FileEntity savedFile = filePsqlDbRepository.save(fileEntity);
    return fileEntitiesMapper.fileFromEntity(savedFile);
  }
}
