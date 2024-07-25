package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * {@link Mapper} Contains methods for transforming core domain objects into dto's and back.
 *
 * <p>Mapstruct generates an implementation based on this interface contract.
 */
@Mapper(componentModel = "spring")
public interface ClassMapper {

  /**
   * Transforms a list of {@link ClassConfiguration} into a list of {@link ClassDTO}.
   *
   * @param classes the list of {@link ClassConfiguration} objects to be transformed.
   * @return a list of {@link ClassDTO} matching the parameter passed to this method.
   */
  List<ClassDTO> classesDTOFrom(List<ClassConfiguration> classes);

  /**
   * Transforms a list of {@link Folder} into a list of {@link FolderDTO}.
   *
   * @param folders the list of {@link Folder} core domain objects to be transformed.
   * @return list of {@link FolderDTO} matching the parameter passed to this method.
   */
  List<FolderDTO> foldersDTOFrom(List<Folder> folders);

  /**
   * Builds a {@link ClassConfiguration} core domain object based on a {@link CreateClassRequest}
   * instance.
   *
   * @param createClassRequest encapsulates the requests parameters.
   * @return a {@link ClassConfiguration} object build based on the request parameter.
   */
  ClassConfiguration fromCreateClassRequest(CreateClassRequest createClassRequest);

  /**
   * Builds a {@link Folder} core domain object based on a {@link CreateFolderRequest} instance.
   *
   * @param createFolderRequest encapsulates the requests parameters.
   * @return a {@link Folder} object build based on the request parameter.
   */
  Folder fromCreateFolderRequest(CreateFolderRequest createFolderRequest);

  @Mapping(source = "newName", target = "name")
  Folder fromRenameFolderRequest(RenameFolderRequest renameFolderRequest);
}
