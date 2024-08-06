package com.ghsbm.group.peer.colab.domain.classes.persistence;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.ClassConfigurationEntity;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.ClassEntitiesMapper;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.EnrolmentEntity;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.EnrolmentId;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.FolderEntity;
import com.ghsbm.group.peer.colab.domain.classes.persistence.repository.ClassPsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.classes.persistence.repository.EnrolmentPsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.classes.persistence.repository.FolderPsqlDbRespository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.UserRepository;
import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;
import java.util.List;
import java.util.Optional;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Implementation of the class repository interface. Reads and persists data into a db. */
@Component
@NoArgsConstructor
@Setter
public class ClassRepositoryAdapter implements ClassRepository {

  private ClassPsqlDbRepository classPsqlDbRepository;
  private FolderPsqlDbRespository folderPsqlDbRespository;
  private UserRepository userRepository;
  private EnrolmentPsqlDbRepository enrolmentPsqlDbRepository;
  private ClassEntitiesMapper classEntitiesMapper;

  @Autowired
  public ClassRepositoryAdapter(
      ClassPsqlDbRepository classPsqlDbRepository,
      FolderPsqlDbRespository folderPsqlDbRespository,
      ClassEntitiesMapper classEntitiesMapper,
      UserRepository userRepository,
      EnrolmentPsqlDbRepository enrolmentPsqlDbRepository) {
    this.classPsqlDbRepository = classPsqlDbRepository;
    this.folderPsqlDbRespository = folderPsqlDbRespository;
    this.classEntitiesMapper = classEntitiesMapper;
    this.userRepository = userRepository;
    this.enrolmentPsqlDbRepository = enrolmentPsqlDbRepository;
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
    return folderPsqlDbRespository.existsByNameAndAndClassConfigurationIdAndParentId(
        folder.getName(), folder.getClassConfigurationId(), folder.getParentId());
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
  public void enrol(String userLogin, String enrolmentKey) {
    UserEntity userEntity =
        userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(
                () ->
                    new IllegalStateException("User with username" + userLogin + "does not exist"));
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
    if (enrolmentPsqlDbRepository.existsById(enrolmentId)) {
      return;
    } else {
      enrolmentPsqlDbRepository.save(new EnrolmentEntity(userEntity, classConfigurationEntity));
    }
  }
}
