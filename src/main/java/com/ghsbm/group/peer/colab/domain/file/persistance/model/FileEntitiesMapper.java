package com.ghsbm.group.peer.colab.domain.file.persistance.model;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import org.mapstruct.Mapper;

/**
 * {@link Mapper}
 *
 * <p>Abstract class containing helper methods for mapping between core and entity objects.
 */
@Mapper(componentModel = "spring")
public abstract class FileEntitiesMapper {
  public abstract File fileFromEntity(FileEntity savedFile);
}
