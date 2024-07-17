package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.FacultyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyPsqlDbRepository extends JpaRepository<FacultyEntity, Long> {

    List<FacultyEntity> findByUniversityId(Long universityId);
}
