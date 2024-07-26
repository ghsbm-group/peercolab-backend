package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/** JPA repository for {@link FolderEntity} */
@Repository
public interface FolderPsqlDbRespository extends JpaRepository<FolderEntity, Long> {
  /**
   * retrieves a list of {@link FolderEntity} belonging to a class configuration and parent id set
   * to null.
   *
   * @param classConfigurationId the class configuration id for which the folders are retrieved.
   * @return a list of {@link FolderEntity} based on the classConfigurationId.
   */
  List<FolderEntity> findByClassConfigurationIdAndParentIdNull(Long classConfigurationId);

  /**
   * retrieves a list of {@link FolderEntity} belonging to a folder or subfolder.
   *
   * @param parentId the folder id for which the folders are retrieved.
   * @return a list of {@link FolderEntity} based on the parentId.
   */
  List<FolderEntity> findByParentId(Long parentId);

  /**
   * receives the id of the folder and the new name that will be set to the folder
   *
   * @param id id of the folder that will be update
   * @param name the new name that will be set to the folder
   */
  @Modifying
  @Query("update FolderEntity f set f.name = :name where f.id = :id")
  void updateFolderName(@Param(value = "id") Long id, @Param(value = "name") String name);
}
