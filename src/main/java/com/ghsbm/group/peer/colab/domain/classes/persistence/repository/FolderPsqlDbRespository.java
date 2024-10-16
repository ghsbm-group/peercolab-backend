package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.FolderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

  /**
   * checks if an entity of {@link FolderEntity} already exists with the specific parameters.
   *
   * @param name the name of the folder
   * @param classConfigurationId the class configuration id that belongs to
   * @param parentId the folder id that belongs to
   * @return if the {@link FolderEntity} exists or not (true or false)
   */
  boolean existsByNameAndAndClassConfigurationIdAndParentId(
      String name, Long classConfigurationId, Long parentId);

  /**
   * checks if an entity of {@link FolderEntity} already exists with the specific parameters.
   *
   * @param name the name of the folder
   * @param classConfigurationId the class configuration id that belongs to
   * @return if the {@link FolderEntity} exists or not (true or false)
   */
  boolean existsByNameAndAndClassConfigurationId(String name, Long classConfigurationId);

  /**
   * Counts all the subfolder of a {@link FolderEntity}
   *
   * @param parentId the id of the folder for which the number of subfolders is returned
   * @return the number of subfolders of the specified folder
   */
  @Query(
      value =
          "WITH RECURSIVE folder_hierarchy AS ("
              + "SELECT f.id, f.parent_id "
              + "FROM folder f "
              + "WHERE f.parent_id = :parentId "
              + "UNION ALL "
              + "SELECT f.id, f.parent_id "
              + "FROM folder f "
              + "INNER JOIN folder_hierarchy fh ON f.parent_id = fh.id "
              + ") "
              + "SELECT COUNT(*) FROM folder_hierarchy",
      nativeQuery = true)
  long countAllSubfolders(@Param("parentId") long parentId);

  /**
   * Counts all the posted messages in all message boards that belongs to a folder
   *
   * @param folderId the id of the folder for which the number of posted messages is returned
   * @return The number of posted messages
   */
  @Query(
      value =
          "WITH RECURSIVE folder_hierarchy AS ("
              + "    SELECT f.id, f.parent_id, f.is_messageboard "
              + "    FROM folder f "
              + "    WHERE f.id = :folderId "
              + "    UNION ALL "
              + "    SELECT f.id, f.parent_id, f.is_messageboard "
              + "    FROM folder f "
              + "    INNER JOIN folder_hierarchy fh ON f.parent_id = fh.id "
              + ") "
              + "SELECT COUNT(m.id) AS message_count "
              + "FROM folder_hierarchy fh "
              + "JOIN message m ON fh.id = m.messageboard_id "
              + "WHERE fh.is_messageboard = true",
      nativeQuery = true)
  long countMessages(@Param("folderId") Long folderId);

  /**
   * Return a {@link List<Long>} that contains the ids of a message board belonging to a folder
   *
   * @param parentId the id of the folder where the message boards are located
   * @return a {@link List<Long>} with the ids of a message board
   */
  @Query(
      value =
          "WITH RECURSIVE folder_hierarchy AS ( "
              + "  SELECT id, is_messageboard"
              + "  FROM folder "
              + "  WHERE id = :parentId "
              + "  UNION ALL "
              + "  SELECT f.id, f.is_messageboard "
              + "  FROM folder f "
              + "  INNER JOIN folder_hierarchy fh ON f.parent_id = fh.id "
              + ") "
              + "SELECT id "
              + "FROM folder_hierarchy "
              + "WHERE is_messageboard = true",
      nativeQuery = true)
  List<Long> findMessageBoardsIds(@Param("parentId") Long parentId);

  FolderEntity findFirstById(Long id);

  @Query("SELECT COUNT(m) " +
          "FROM MessageEntity m " +
          "LEFT JOIN UserMessageboardAccessEntity lam ON lam.messageboard.id = m.messageboardId AND lam.user.id = :userId " +
          "WHERE m.messageboardId IN :messageBoardIds " +
          "AND (lam IS NULL OR lam.lastAccessDate IS NULL OR m.postDate > lam.lastAccessDate)")
  Long countUnreadMessagesByUserAndMessageBoards(@Param("userId") Long userId, @Param("messageBoardIds") List<Long> messageBoardIds);
}
