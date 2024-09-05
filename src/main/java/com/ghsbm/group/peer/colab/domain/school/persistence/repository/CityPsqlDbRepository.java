package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.CityEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** JPA repository for {@link CityEntity} */
@Repository
public interface CityPsqlDbRepository extends JpaRepository<CityEntity, Long> {
  /**
   * retrieves a list of {@link CityEntity} belonging to a country.
   *
   * @param countryId the country id for which the cities are retrieved.
   * @return a list of {@link CityEntity} based on the countryId.
   */
  List<CityEntity> findByCountryIdOrderByName(Long countryId);
}
