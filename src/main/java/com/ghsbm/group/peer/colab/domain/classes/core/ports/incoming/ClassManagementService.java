package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming;

import java.util.List;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassDetails;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import com.ghsbm.group.peer.colab.domain.classes.core.model.FolderInformation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface contract between the API and the core business logic.
 *
 * <p>Contains methods used for the management of classes.
 */
public interface ClassManagementService {

  /**
   * Retrieves the class configurations associated with a department.
   *
   * @param departmentId the department id for which the class configurations are retrieved.
   * @return a list of {@link ClassConfiguration} associated with the provided department id.
   */
  List<ClassConfiguration> retrieveClassByDepartmentId(Long departmentId);

  /**
   * Retrieves the folders associated with a class configuration and which are not subfolders, i.e.
   * parentId is null
   *
   * @param classConfigurationId the class configuration id for which the folders are retrieved.
   * @return a list of {@link Folder} associated with the provided class configuration id.
   */
  List<Folder> retrieveRootFolderByClassConfigurationId(Long classConfigurationId);

  /**
   * Retrieves the subfolders associated with a folder or subfolder
   *
   * @param parentId the folder id for which the folders are retrieved.
   * @return a list of {@link Folder} associated with the provided folder id.
   */
  List<Folder> retrieveFolderByParentId(Long parentId);

  /**
   * Creates a class configuration and an initial folder structure for that class based on the
   * number of years and number of semesters parameters configured.If the user has the authority
   * STUDENT_ADMIN, then will be automatically enrolled in the specific class.
   *
   * <p>Ex: noOfYears = 2, noOfSemesters =2, the following structure will be created: {@code {
   * ['Year ',['Semester 1', 'Semester 2'], 'Year 2, ['Semester 1', 'Semester 2'] ] } }
   *
   * @param classConfigurationInfo the class configuration based on which the class and class
   *     structure is created.
   * @return a {@link ClassDetails} object.
   */
  @Transactional
  ClassDetails createClass(ClassConfiguration classConfigurationInfo);

  /**
   * Persists the folder.
   *
   * @param folder encapsulates folder data.
   * @return a {@link Folder} object with the id attribute set and isMessageBoard parameter set to
   *     false.
   */
  @Transactional
  Folder createFolder(Folder folder);

  /**
   * Persists the message board.
   *
   * @param folder encapsulates message board data.
   * @return a {@link Folder} object with the id attribute set and isMessageBoard parameter set to
   *     true.
   */
  @Transactional
  Folder createMessageBoard(Folder folder);

  /**
   * Rename the folder.
   *
   * @param folder encapsulates folder data.
   * @return a {@link Folder} object with attributes set.
   */
  @Transactional
  Folder renameFolder(Folder folder);

  /**
   * Enrols the current logged-in user into the class defined by this enrolmentKey.
   *
   * @param enrolmentKey the key used to find the class in which the user will be enrolled.
   * @return a {@link ClassDetails} object containing data about the class in which the user was
   *     enrolled.
   */
  @Transactional
  ClassDetails enrolStudent(String enrolmentKey);

  /**
   * Retrieves the enrolment key associated with a class configuration
   *
   * @param classConfigurationId the class configuration id.
   * @return the enrolment key associated with a class configuration
   */
  @Transactional
  String getEnrolmentKeyByClassConfigurationId(Long classConfigurationId);

  /**
   * Check if the user is enrolled in a class to post/read messages, otherwise an exception is
   * thrown.
   *
   * @param messageBoardId the identifier for the message board in which the action will be
   *     performed
   */
  @Transactional(readOnly = true)
  boolean userIsEnrolled(Long messageBoardId);

  /**
   * Get information about a folder, for example: the number of subfolders associated with the
   * folder, the number of posted messages in the folder hierarchy, information about the last
   * message posted
   *
   * @param folderId the identifier of the folder for which the information is obtained
   * @return a {@link FolderInformation} which encapsulates all the required information
   */
  @Transactional
  FolderInformation retrieveFolderInformation(long folderId);

  /**
   * Retrieves the ids of the message board associated with a folder
   *
   * @param folderId the identifier of the folder
   * @return a {@link List<Long>} which encapsulates the ids of the messages board
   */
  @Transactional
  List<Long> getMessageBoardsIds(Long folderId);
}
