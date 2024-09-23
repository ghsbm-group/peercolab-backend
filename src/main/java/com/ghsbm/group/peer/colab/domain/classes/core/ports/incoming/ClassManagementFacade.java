package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming;

import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.STUDENT_ADMIN;
import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.USER_MUST_BE_LOGGED_IN;

import com.ghsbm.group.peer.colab.domain.classes.core.model.*;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.ClassConfigurationAlreadyExistsException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.ClassConfigurationDoesNotExistsException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.FolderAlreadyExistsException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.UserIsNotEnrolledInClassConfigurationException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants;
import com.ghsbm.group.peer.colab.infrastructure.RandomUtil;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;
import java.util.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/** Service that contains the core business logic. */
@Service
public class ClassManagementFacade implements ClassManagementService {

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
    validateUserEnrolment(classConfigurationId);
    return classRepository.findRootFoldersByClassConfiguration(classConfigurationId);
  }

  private void validateUserEnrolment(Long classConfigurationId) {
    String currentUser =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    if (SecurityUtils.hasCurrentUserNoneOfAuthorities(AuthoritiesConstants.ADMIN)
        && !classRepository.isEnrolled(currentUser, classConfigurationId)) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Folder> retrieveRootFolderByClassConfigurationIdEnrollmentClass(
      Long classConfigurationId) {
    return classRepository.findRootFoldersByClassConfiguration(classConfigurationId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Folder> retrieveFolderByParentId(Long parentId) {
    Long classConfigurationId = classRepository.getClassConfigurationByFolderId(parentId).getId();
    validateUserEnrolment(classConfigurationId);
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

    if (classRepository.classDoesNotExists(folder.getClassConfigurationId())) {
      throw new ClassConfigurationDoesNotExistsException();
    }
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
    if (classRepository.classDoesNotExists(folder.getClassConfigurationId())) {
      throw new ClassConfigurationDoesNotExistsException();
    }
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
                .folders(
                    retrieveRootFolderByClassConfigurationIdEnrollmentClass(
                        classConfiguration.getId()))
                .build())
        .enrolmentKey(enrolmentKey)
        .build();
  }

  @Override
  public void enrolStudent(String enrolmentKey, User user) {
    String userLogin = user.getLogin();
    classRepository.enrol(userLogin, enrolmentKey);
  }

  @Override
  public ClassConfiguration retrieveClassConfigurationByClassId(Long classId) {
    return classRepository.getClassConfigurationByClassId(classId);
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

  /**
   * @inheritDoc
   */
  @Override
  public List<ClassConfiguration> getEnrolledClassOfCurrentUser() {
    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    return classRepository.getEnrolmentByUserLogin(userLogin);
  }

  @Override
  public List<Folder> getFolderPath(Long id) {
    List<Folder> path = new ArrayList<Folder>();
    Folder folder = classRepository.findFolderById(id);
    path.add(folder);
    while (folder.getParentId() != null) {
      folder = classRepository.findFolderById(folder.getParentId());
      path.add(folder);
    }
    Collections.reverse(path);
    return path;
  }

  @Override
  public ClassConfiguration retrieveClassConfigurationByFolderId(Long folderId) {
    return classRepository.getClassConfigurationByFolderId(folderId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public UserMessageBoardAccess saveOrUpdateUserMessageboardAccess(Long messageBoardId) {
    return classRepository.saveOrUpdateUserAccessTime(messageBoardId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public UserMessageBoardAccess findUserMessageBoardAccess(Long messageboardId) {
    return classRepository.findByUserAndMessageBoardAccess(messageboardId);
  }

  @Override
  public Long countAllMessagesByMessageBoardId(Long folderId) {
    return classRepository.countMessages(folderId);
  }

  @Override
  public void deleteFolder(Long folderId) {
    Long classId = classRepository.getClassConfigurationByFolderId(folderId).getId();
    validateUserEnrolment(classId);
    var numberOfSubfolders = classRepository.countAllSubfolders(folderId);
    var numberOfPosts = classRepository.countMessages(folderId);
    if (numberOfPosts > 0 || numberOfSubfolders > 0) {
      throw new BadRequestAlertException(
          "Trying to delete a non empty folder", "Folder", "Folder_deletion");
    }

    classRepository.deleteFolder(folderId);
  }

  @Override
  public void changeClassName(Long classId, String name) {
    validateUserEnrolment(classId);
    classRepository.changeClassName(classId, name);
  }
}
