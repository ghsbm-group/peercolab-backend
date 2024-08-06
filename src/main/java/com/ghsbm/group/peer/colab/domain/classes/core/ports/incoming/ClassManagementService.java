package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassDetails;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import java.util.List;
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
   * number of years and number of semesters parameters configured.
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
   * Persists the folder/messageboard.
   *
   * @param folder encapsulates folder data.
   * @return a {@link Folder} object with the id attribute set.
   */
  @Transactional
  Folder createFolder(Folder folder);

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
   * @param enrolmentKey the key used to find the class in which the user will be enroled.
   */
  @Transactional
  void enrolStudent(String enrolmentKey);
}
