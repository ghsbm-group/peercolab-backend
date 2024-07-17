package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.UniversityEntity;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityPsqlDbRepository extends JpaRepository<UniversityEntity, Long> {

  UniversityEntity findByNameAndCity(String name, CityEntity cityEntity);

  List<UniversityEntity> findByCityId(Long cityId);
}
