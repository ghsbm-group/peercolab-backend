package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.classes.core.model.*;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.ClassConfigurationAlreadyExistsException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.FolderAlreadyExistsException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.infrastructure.RandomUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

/** Service that contains the core business logic. */
@Service
class ClassManagementFacade implements ClassManagementService {

  private final ClassRepository classRepository;

  public ClassManagementFacade(ClassRepository classRepository) {
    this.classRepository = classRepository;
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<ClassConfiguration> retrieveClassByDepartmentId(final Long departmentId) {
    return classRepository.findClassesByDepartment(departmentId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Folder> retrieveRootFolderByClassConfigurationId(Long classConfigurationId) {
    return classRepository.findRootFoldersByClassConfiguration(classConfigurationId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Folder> retrieveFolderByParentId(Long parentId) {
    return classRepository.findFoldersByParentId(parentId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public ClassDetails createClass(final ClassConfiguration classConfigurationInfo) {
    if (classRepository.classConfigurationAlreadyExists(classConfigurationInfo)) {
      throw new ClassConfigurationAlreadyExistsException();
    }
    String enrolmentKey = RandomUtil.generateClassEnrolmentKey();
    final ClassConfiguration classConfiguration = classRepository.create(classConfigurationInfo, enrolmentKey);

    // method returns ClassDetails type that contains ClassStructure and ClassDetails
    final ClassDetails classDetails = new ClassDetails();
    classDetails.setEnrolmentKey(enrolmentKey);
    final ClassStructure classStructure = new ClassStructure();
    final List<Folder> folders = new ArrayList<>();

    for (int i = 1;
        i <= classConfigurationInfo.getNoOfStudyYears();
        i++) { // for creating years folders
      final Folder yearFolder =
          Folder.builder()
              .name("Year " + i)
              .isMessageBoard(false)
              .classConfigurationId(classConfiguration.getId())
              .build();

      final Folder persistedCurrentYearFolder = classRepository.create(yearFolder);
      folders.add(persistedCurrentYearFolder);

      for (int j = 1;
          j <= classConfigurationInfo.getNoOfSemestersPerYear();
          j++) { // for creating semesters folders
        final Folder semesterFolder =
            Folder.builder()
                .name("Semester " + j)
                .isMessageBoard(false)
                .parentId(persistedCurrentYearFolder.getId())
                .classConfigurationId(classConfiguration.getId())
                .build();
        classRepository.create(semesterFolder);
      }
    }
    classStructure.setFolders(folders);
    classDetails.setClassStructure(classStructure);
    classDetails.setClassConfiguration(classConfiguration);

    return classDetails;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Folder createFolder(Folder folder) {
    Objects.requireNonNull(folder);
    Objects.requireNonNull(folder.getName());
    Objects.requireNonNull(folder.getClassConfigurationId());
    if (classRepository.folderAlreadyExists(folder)) {
      throw new FolderAlreadyExistsException();
    }
    return classRepository.create(folder);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Folder renameFolder(Folder folder) {
    Folder folderWithNewNameSet = classRepository.findFolderById(folder.getId());
    folderWithNewNameSet.setName(folder.getName());

    if (classRepository.folderAlreadyExists(folderWithNewNameSet)) {
      throw new FolderAlreadyExistsException();
    }
    return classRepository.renameFolder(folder);
  }
}
