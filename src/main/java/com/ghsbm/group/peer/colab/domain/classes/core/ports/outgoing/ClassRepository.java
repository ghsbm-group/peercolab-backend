package com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Message;

import java.util.List;

/**
 * Interface contract between core business logic and the persistence layer.
 *
 * <p>Contains methods for persisting and reading data.
 */
public interface ClassRepository {

  /**
   * Retrieves all class configurations belonging to this department.
   *
   * @param departmentId The department id for which the class configurations are returned.
   * @return a list of {@link ClassConfiguration} entities.
   */
  List<ClassConfiguration> findClassesByDepartment(Long departmentId);

  /**
   * Retrieves all folders that are type of root, i.e. parentId is set to null, belonging to this
   * class configuration.
   *
   * @param classConfigurationId The class configuration id for which the folders are returned.
   * @return a list of {@link Folder} entities.
   */
  List<Folder> findRootFoldersByClassConfiguration(Long classConfigurationId);

  /**
   * Retrieves all folders belonging to this folder.
   *
   * @param parentId The folder id for which the folders are returned.
   * @return a list of {@link Folder} entities.
   */
  List<Folder> findFoldersByParentId(Long parentId);

  /**
   * Persists a class configuration.
   *
   * @param classConfigurationInfo the class configuration to be persisted.
   * @param enrolmentKey the key used to enrol students in this class.
   * @return A {@link ClassConfiguration} with the id set.
   */
  ClassConfiguration create(ClassConfiguration classConfigurationInfo, String enrolmentKey);

  /**
   * Persists a folder or a message board to the db.
   *
   * @param folder the folder or messageboard to be persisted.
   * @return A {@link Folder} objects with its id set.
   */
  Folder create(Folder folder);

  /**
   * Persists a message to the db.
   *
   * @param message the message to be persisted
   * @return A {@link Message} object with its id set.
   */
  Message create(Message message);

  /**
   * Update the name of the folder in db.
   *
   * @param folder the folder to be updated.
   * @return A {@link Folder} object with the attributes set.
   */
  Folder renameFolder(Folder folder);

  /**
   * Checks if a class configuration already exists in a certain department.
   *
   * @param classConfiguration the class configuration to be checked
   * @return if the specific class configuration exists
   */
  boolean classConfigurationAlreadyExists(ClassConfiguration classConfiguration);

  /**
   * Checks if a folder/messageboard already exists with the same parmeters
   *
   * @param folder the folder to be checked
   * @return if the specific folder exists
   */
  boolean folderAlreadyExists(Folder folder);

  /**
   * Find a folder by its id
   *
   * @param folderId the folder id
   * @return A {@link Folder} object
   */
  Folder findFolderById(Long folderId);
}
