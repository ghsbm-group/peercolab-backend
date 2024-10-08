package com.ghsbm.group.peer.colab.domain.classes.persistence;

import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.USER_MUST_BE_LOGGED_IN;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import com.ghsbm.group.peer.colab.domain.classes.core.model.UserMessageBoardAccess;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.*;
import com.ghsbm.group.peer.colab.domain.classes.persistence.repository.ClassPsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.classes.persistence.repository.EnrolmentPsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.classes.persistence.repository.FolderPsqlDbRespository;
import com.ghsbm.group.peer.colab.domain.classes.persistence.repository.UserMessageBoardAccessPsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.UserRepository;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ClassRepositoryAdapter implements ClassRepository {

  private ClassPsqlDbRepository classPsqlDbRepository;
  private FolderPsqlDbRespository folderPsqlDbRespository;
  private UserMessageBoardAccessPsqlDbRepository userMessageBoardAccessPsqlDbRepository;
  private UserRepository userRepository;
  private EnrolmentPsqlDbRepository enrolmentPsqlDbRepository;
  private ClassEntitiesMapper classEntitiesMapper;

  @Autowired
  public ClassRepositoryAdapter(
      ClassPsqlDbRepository classPsqlDbRepository,
      FolderPsqlDbRespository folderPsqlDbRespository,
      UserMessageBoardAccessPsqlDbRepository userMessageBoardAccessPsqlDbRepository,
      ClassEntitiesMapper classEntitiesMapper,
      UserRepository userRepository,
      EnrolmentPsqlDbRepository enrolmentPsqlDbRepository) {
    this.classPsqlDbRepository = classPsqlDbRepository;
    this.folderPsqlDbRespository = folderPsqlDbRespository;
    this.classEntitiesMapper = classEntitiesMapper;
    this.userMessageBoardAccessPsqlDbRepository = userMessageBoardAccessPsqlDbRepository;
    this.enrolmentPsqlDbRepository = enrolmentPsqlDbRepository;
    this.userRepository = userRepository;
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<ClassConfiguration> findClassesByDepartment(Long departmentId) {
    return classEntitiesMapper.fromClassEntities(
        classPsqlDbRepository.findByDepartmentId(departmentId));
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Folder> findRootFoldersByClassConfiguration(Long classConfigurationId) {
    return classEntitiesMapper.fromFolderEntities(
        folderPsqlDbRespository.findByClassConfigurationIdAndParentIdNull(classConfigurationId));
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Folder> findFoldersByParentId(Long parentId) {
    return classEntitiesMapper.fromFolderEntities(folderPsqlDbRespository.findByParentId(parentId));
  }

  /**
   * @inheritDoc
   */
  @Override
  public ClassConfiguration create(ClassConfiguration classConfigurationInfo, String enrolmentKey) {
    final var savedClass =
        classPsqlDbRepository.save(
            ClassConfigurationEntity.builder()
                .name(classConfigurationInfo.getName())
                .startYear(classConfigurationInfo.getStartYear())
                .noOfStudyYears(classConfigurationInfo.getNoOfStudyYears())
                .noOfSemestersPerYear(classConfigurationInfo.getNoOfSemestersPerYear())
                .departmentId(classConfigurationInfo.getDepartmentId())
                .enrolmentKey(enrolmentKey)
                .build());
    return classEntitiesMapper.classFromEntity(savedClass);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Folder create(Folder folder) {
    final var classEntity =
        classPsqlDbRepository.getReferenceById(folder.getClassConfigurationId());
    var folderEntity = Optional.<FolderEntity>empty();
    if (folder.getParentId() != null) {
      folderEntity = folderPsqlDbRespository.findById(folder.getParentId());
    }
    final var savedFolder =
        folderPsqlDbRespository.save(
            FolderEntity.builder()
                .name(folder.getName())
                .classConfiguration(classEntity)
                .description(folder.getDescription())
                .isMessageBoard(folder.getIsMessageBoard())
                .parent(folderEntity.orElse(null))
                .build());

    return classEntitiesMapper.folderFromEntity(savedFolder);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Folder renameFolder(Folder folder) {
    folderPsqlDbRespository.updateFolderName(folder.getId(), folder.getName());
    return classEntitiesMapper.folderFromEntity(
        folderPsqlDbRespository.getReferenceById(folder.getId()));
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean classConfigurationAlreadyExists(ClassConfiguration classConfiguration) {
    return classPsqlDbRepository.existsByNameAndStartYear(
        classConfiguration.getName(), classConfiguration.getStartYear());
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean folderAlreadyExists(Folder folder) {

    if (folder.getParentId() == null) {
      return folderPsqlDbRespository.existsByNameAndAndClassConfigurationId(
          folder.getName(), folder.getClassConfigurationId());
    }
    return folderPsqlDbRespository.existsByNameAndAndClassConfigurationIdAndParentId(
        folder.getName(), folder.getClassConfigurationId(), folder.getParentId());
  }

  @Override
  public boolean classDoesNotExists(Long classConfigurationId) {
    return !classPsqlDbRepository.existsById(classConfigurationId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Folder findFolderById(Long folderId) {
    return classEntitiesMapper.folderFromEntity(folderPsqlDbRespository.getReferenceById(folderId));
  }

  /**
   * @inheritDoc
   */
  @Override
  public ClassConfiguration enrol(String userLogin, String enrolmentKey) {
    UserEntity userEntity =
        userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        "User with username " + userLogin + " does not exist"));
    ClassConfigurationEntity classConfigurationEntity =
        classPsqlDbRepository
            .findByEnrolmentKey(enrolmentKey)
            .orElseThrow(
                () ->
                    new BadRequestAlertException(
                        "Invalid enrolment key", "class", "invalid.enrlomentKey"));
    EnrolmentId enrolmentId =
        EnrolmentId.builder()
            .classConfigurationId(classConfigurationEntity.getId())
            .userId(userEntity.getId())
            .build();
    if (!enrolmentPsqlDbRepository.existsById(enrolmentId)) {
      enrolmentPsqlDbRepository.save(new EnrolmentEntity(userEntity, classConfigurationEntity));
    }

    return classEntitiesMapper.classFromEntity(classConfigurationEntity);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean isEnrolled(String userLogin, Long classConfigurationId) {
    return enrolmentPsqlDbRepository.existsByUserNameAndClassConfigurationId(
        userLogin, classConfigurationId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Optional<String> getEnrolmentKeyByClassConfigurationId(Long classConfigurationId) {
    return classPsqlDbRepository.findEnrolmentKeyById(classConfigurationId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public long countAllSubfolders(long folderId) {
    return folderPsqlDbRespository.countAllSubfolders(folderId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Long> findMessageBoardsIds(Long folderId) {
    return folderPsqlDbRespository.findMessageBoardsIds(folderId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public long countMessages(long folderId) {
    return folderPsqlDbRespository.countMessages(folderId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<ClassConfiguration> getEnrolmentByUserLogin(String login) {

    return classEntitiesMapper.fromClassEntities(
        enrolmentPsqlDbRepository.findByUserLogin(login).stream()
            .map(EnrolmentEntity::getClassConfiguration)
            .collect(Collectors.toList()));
  }

  @Override
  public ClassConfiguration getClassConfigurationByFolderId(Long folderId) {
    return classEntitiesMapper.classFromEntity(
        folderPsqlDbRespository.findFirstById(folderId).getClassConfiguration());
  }

  /**
   * @inheritDoc
   */
  @Override
  public UserMessageBoardAccess saveOrUpdateUserAccessTime(Long messageboardId) {
    ZonedDateTime lastAccesDate = ZonedDateTime.now();

    UserEntity userEntity =
        userRepository
            .findOneByLogin(
                SecurityUtils.getCurrentUserLogin()
                    .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN)))
            .orElseThrow(() -> new IllegalStateException("User does not exists"));
    FolderEntity messageBoardEntity =
        folderPsqlDbRespository
            .findById(messageboardId)
            .orElseThrow(() -> new IllegalStateException("Messageboard does not exists"));
    UserMessageboardAccessEntity userMessageboardAccessEntity =
        new UserMessageboardAccessEntity(userEntity, messageBoardEntity, lastAccesDate);

    userMessageBoardAccessPsqlDbRepository.save(userMessageboardAccessEntity);
    return UserMessageBoardAccess.builder()
        .userId(userEntity.getId())
        .messageboardId(messageBoardEntity.getId())
        .lastAccessDate(lastAccesDate)
        .build();
  }

  /**
   * @inheritDoc
   */
  @Override
  public UserMessageBoardAccess findByUserAndMessageBoardAccess(Long messageboardId) {
    String login =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    return classEntitiesMapper.fromUserMeessageBoardAccesEntity(
        userMessageBoardAccessPsqlDbRepository.findByUser_LoginAndMessageboard_Id(
            login, messageboardId));
  }

  @Override
  public void deleteFolder(Long folderId) {
    userMessageBoardAccessPsqlDbRepository.deleteByMessageboard_Id(folderId);
    folderPsqlDbRespository.deleteById(folderId);
  }

  @Override
  public void changeClassName(Long classId, String name) {
    classPsqlDbRepository.updateNameById(classId, name);
  }

  @Override
  public ClassConfiguration getClassConfigurationByClassId(Long classId) {
    return classEntitiesMapper.classFromEntity(classPsqlDbRepository.getReferenceById(classId));
  }
}
