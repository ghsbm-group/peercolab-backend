package com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import java.util.List;

/**
 * Interface contract between core business logic and the persistence layer.
 *
 * <p>Contains methods for persisting and reading data.
 */
public interface ClassRepository {

  /**
   * Retrieves all class configurations belonging to this department.
   *
   * @param departmentId The department id for which the configurations are returned.
   * @return a list of {@link ClassConfiguration} entities.
   */
  List<ClassConfiguration> findClassesByDepartment(Long departmentId);

  /**
   * Persists a class configuration.
   *
   * @param classConfigurationInfo the class configuration to be persisted.
   * @return A {@link ClassConfiguration} with the id set.
   */
  ClassConfiguration create(ClassConfiguration classConfigurationInfo);

  /**
   * Persists a folder to the db.
   *
   * @param folder the folder to be persisted.
   * @return A {@link Folder} objects with its id set.
   */
  Folder create(Folder folder);
}
