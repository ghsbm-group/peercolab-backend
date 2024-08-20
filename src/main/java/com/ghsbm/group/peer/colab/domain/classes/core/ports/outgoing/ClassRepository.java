package com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;

import java.util.List;
import java.util.Optional;

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

  /**
   * Enrols a user into a class based on the enrolmentKey assosicated to that class.
   *
   * @param userLogin lower case username of that user.
   * @param enrolmentKey the enrolment key of the class.
   */
  ClassConfiguration enrol(String userLogin, String enrolmentKey);

  /**
   * Checks if the user is enroled in a specif message board.
   *
   * @param userLogin the current user logged
   * @param classConfigurationId the class configuration to which user should be enrolled
   * @return if the user is enrolled in a specific class configuration
   */
  boolean isEnrolled(String userLogin, Long classConfigurationId);

  /**
   * Find enrolment key by class configuration id
   *
   * @param classConfigurationId The class configuration id for which the enrolemnt key are returned
   * @return a {@link String} that contains the enrolment key
   */
  Optional<String> getEnrolmentKeyByClassConfigurationId(Long classConfigurationId);

  /**
   * Counts all the subfolder of a folder
   *
   * @param folderId the id of the folder
   * @return the number of subfolders of the specified folder
   */
  long countAllSubfolders(long folderId);

  /**
   * Retrieves all message boards ids belonging to this folder.
   *
   * @param folderId the id of the folder
   * @return a list of the ids of a message board
   */
  List<Long> findMessageBoardsIds(Long folderId);

  /**
   * Counts all the posted messages in message boards of a folder
   *
   * @param folderId the id of the folder
   * @return the number of posted messages
   */
  long countMessages(long folderId);
}
