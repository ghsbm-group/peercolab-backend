package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.CityEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityPsqlDbRepository extends JpaRepository<CityEntity, Long> {
  List<CityEntity> findByCountryId(Long countryId);
}
