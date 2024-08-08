package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.classes.core.model.*;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.ClassConfigurationAlreadyExistsException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.FolderAlreadyExistsException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.UserIsNotEnrolledInClassConfigurationException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing.UserManagementRepository;
import com.ghsbm.group.peer.colab.infrastructure.RandomUtil;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.ADMIN;
import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.USER_MUST_BE_LOGGED_IN;

/** Service that contains the core business logic. */
@Service
class ClassManagementFacade implements ClassManagementService {

  private final ClassRepository classRepository;
  private final UserManagementRepository userManagementRepository;

  public ClassManagementFacade(
      ClassRepository classRepository, UserManagementRepository userManagementRepository) {
    this.classRepository = classRepository;
    this.userManagementRepository = userManagementRepository;
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
  public List<PostedMessage> retrieveMessagesByMessageboardId(Long messageboardId) {

    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    Folder folder = classRepository.findFolderById(messageboardId);

    if (!classRepository.isEnrolled(userLogin, folder.getClassConfigurationId())
        && !SecurityUtils.hasCurrentUserThisAuthority(ADMIN)) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }

    List<Message> messages = classRepository.findMessagesByMessageBoardId(messageboardId);
    List<PostedMessage> list = new ArrayList<PostedMessage>(messages.size());
    for (Message message : messages) {
      list.add(messageToPostedMessage(message));
    }
    return list;
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
    return classRepository.create(folder);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Message createMessage(Message message) {
    Objects.requireNonNull(message);
    Objects.requireNonNull(message.getContent());
    Objects.requireNonNull(message.getMessageboardId());
    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    Folder folder = classRepository.findFolderById(message.getMessageboardId());
    if (!classRepository.isEnrolled(userLogin, folder.getClassConfigurationId())) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }
    return classRepository.create(message);
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
   * Transform a {@link Message} into a {@link PostedMessage}
   *
   * @param message a {@link Message} object
   * @return a {@link PostedMessage} object
   */
  protected PostedMessage messageToPostedMessage(Message message) {
    if (message == null) {
      return null;
    }
    return PostedMessage.builder()
        .id(message.getId())
        .content(message.getContent())
        .userId(message.getUserId())
        .postDate(message.getPostDate())
        .login(userManagementRepository.findUserById(message.getUserId()).get().getLogin())
        .build();
  }
}
