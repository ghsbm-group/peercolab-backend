package com.ghsbm.group.peer.colab.domain.classes.persistence.model;

import com.ghsbm.group.peer.colab.domain.classes.core.model.*;
import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * {@link Mapper}
 *
 * <p>Abstract class containing helper methods for mapping between core and entity objects.
 */
@Mapper(componentModel = "spring")
public abstract class ClassEntitiesMapper {

  public abstract List<ClassConfiguration> fromClassEntities(
      List<ClassConfigurationEntity> classes);

  public abstract List<Folder> fromFolderEntities(List<FolderEntity> folders);

  public abstract ClassConfiguration classFromEntity(ClassConfigurationEntity savedClass);

  @Mappings({
    @Mapping(target = "classConfigurationId", source = "classConfiguration.id"),
    @Mapping(target = "parentId", source = "parent.id")
  })
  public abstract Folder folderFromEntity(FolderEntity savedFolder);

  public abstract List<Enrolment> fromEnrolmentEntities(List<EnrolmentEntity> enrolmentEntities);

  @Mappings({
          @Mapping(target = "userId", source = "user.id"),
          @Mapping(target = "messageboardId", source = "messageboard.id")
  })
  public abstract UserMessageBoardAccess fromUserMeessageBoardAccesEntity(UserMessageboardAccessEntity entity);
}
