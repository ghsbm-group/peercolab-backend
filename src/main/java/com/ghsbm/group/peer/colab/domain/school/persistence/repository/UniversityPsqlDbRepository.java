package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.UniversityEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** JPA repository for {@link UniversityEntity} */
@Repository
public interface UniversityPsqlDbRepository extends JpaRepository<UniversityEntity, Long> {

  /**
   * retrieves a list of {@link UniversityEntity} belonging to a city.
   *
   * @param cityId the city id for which the universities are retrieved.
   * @return a list of {@link UniversityEntity} based on the cityId.
   */
  List<UniversityEntity> findByCityId(Long cityId);
}
