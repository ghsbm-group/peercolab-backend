package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming;

import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.STUDENT_ADMIN;
import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.USER_MUST_BE_LOGGED_IN;

import com.ghsbm.group.peer.colab.domain.classes.core.model.*;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.ClassConfigurationAlreadyExistsException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.FolderAlreadyExistsException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.infrastructure.RandomUtil;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/** Service that contains the core business logic. */
@Service
class ClassManagementFacade implements ClassManagementService {

  private final ClassRepository classRepository;
  private final MessageSource messageSource;

  public ClassManagementFacade(ClassRepository classRepository, MessageSource messageSource) {
    this.classRepository = classRepository;
    this.messageSource = messageSource;
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
    final ClassConfiguration classConfiguration =
        classRepository.create(classConfigurationInfo, enrolmentKey);

    final List<Folder> folders = new ArrayList<>();
    for (int i = 1;
        i <= classConfigurationInfo.getNoOfStudyYears();
        i++) { // for creating years folders
      final Folder yearFolder =
          Folder.builder()
              .name(messageSource.getMessage("year." + i, null, LocaleContextHolder.getLocale()))
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
                .name(
                    messageSource.getMessage(
                        "semester." + j, null, LocaleContextHolder.getLocale()))
                .isMessageBoard(false)
                .parentId(persistedCurrentYearFolder.getId())
                .classConfigurationId(classConfiguration.getId())
                .build();
        classRepository.create(semesterFolder);
      }
    }
    if (SecurityUtils.hasCurrentUserAnyOfAuthorities(STUDENT_ADMIN)) {
      enrolStudent(enrolmentKey);
    }
    return ClassDetails.builder()
        .enrolmentKey(enrolmentKey)
        .classStructure(ClassStructure.builder().folders(folders).build())
        .classConfiguration(classConfiguration)
        .build();
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
    folder.setIsMessageBoard(false);
    return classRepository.create(folder);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Folder createMessageBoard(Folder folder) {
    Objects.requireNonNull(folder);
    Objects.requireNonNull(folder.getName());
    Objects.requireNonNull(folder.getClassConfigurationId());

    if (classRepository.folderAlreadyExists(folder)) {
      throw new FolderAlreadyExistsException();
    }
    folder.setIsMessageBoard(true);
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

  /**
   * @inheritDoc
   */
  @Override
  public ClassDetails enrolStudent(String enrolmentKey) {
    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    ClassConfiguration classConfiguration = classRepository.enrol(userLogin, enrolmentKey);

    return ClassDetails.builder()
        .classConfiguration(classConfiguration)
        .classStructure(
            ClassStructure.builder()
                .folders(retrieveRootFolderByClassConfigurationId(classConfiguration.getId()))
                .build())
        .enrolmentKey(enrolmentKey)
        .build();
  }

  /**
   * @inheritDoc
   */
  @Override
  public String getEnrolmentKeyByClassConfigurationId(Long classConfigurationId) {
    return classRepository
        .getEnrolmentKeyByClassConfigurationId(classConfigurationId)
        .orElseThrow(() -> new IllegalStateException("Class not found"));
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean userIsEnrolled(Long messageBoardId) {
    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    Folder folder = classRepository.findFolderById(messageBoardId);
    return classRepository.isEnrolled(userLogin, folder.getClassConfigurationId());
  }

  /**
   * @inheritDoc
   */
  @Override
  public FolderInformation retrieveFolderInformation(long folderId) {
    var numberOfSubfolers = classRepository.countAllSubfolders(folderId);
    var numberOfPosts = classRepository.countMessages(folderId);

    return FolderInformation.builder().topics(numberOfSubfolers).posts(numberOfPosts).build();
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Long> getMessageBoardsIds(Long folderId) {
    return classRepository.findMessageBoardsIds(folderId);
  }
}
