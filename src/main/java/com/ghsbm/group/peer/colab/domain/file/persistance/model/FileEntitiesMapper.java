package com.ghsbm.group.peer.colab.domain.file.persistance.model;

import com.ghsbm.group.peer.colab.domain.file.core.model.FileInfo;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * {@link Mapper}
 *
 * <p>Abstract class containing helper methods for mapping between core and entity objects.
 */
@Mapper(componentModel = "spring")
public abstract class FileEntitiesMapper {
  public abstract FileInfo fileFromEntity(FileEntity savedFile);

  public abstract List<FileInfo> map(List<FileEntity> byFolderId);
}
