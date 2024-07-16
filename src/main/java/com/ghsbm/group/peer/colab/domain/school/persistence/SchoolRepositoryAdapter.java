package com.ghsbm.group.peer.colab.domain.school.persistence;

import com.ghsbm.group.peer.colab.domain.school.core.model.City;
import com.ghsbm.group.peer.colab.domain.school.core.model.Faculty;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.FacultyEntity;
import com.ghsbm.group.peer.colab.domain.school.persistence.repository.FacultyPsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.school.core.model.Country;
import com.ghsbm.group.peer.colab.domain.school.core.model.University;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.UniversityEntitiesMapper;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.UniversityEntity;
import com.ghsbm.group.peer.colab.domain.school.persistence.repository.CityPsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.school.persistence.repository.CountryPsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.school.persistence.repository.UniversityPsqlDbRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class SchoolRepositoryAdapter implements SchoolRepository {

  @Autowired
  private CountryPsqlDbRepository countryPsqlDbRepository;

  @Autowired
  private CityPsqlDbRepository cityPsqlDbRepository;

  @Autowired
  private UniversityPsqlDbRepository universityPsqlDbRepository;

  @Autowired
  private FacultyPsqlDbRepository facultyPsqlDbRepository;

  @Autowired
  private UniversityEntitiesMapper universityEntitiesMapper;

  @Override
  public List<City> findCitiesByCountry(Long countryId) {
    return universityEntitiesMapper.fromCityEntities(
        cityPsqlDbRepository.findByCountryId(countryId));
  }

  @Override
  public University create(final University university) {
    final var cityEntity = cityPsqlDbRepository.getReferenceById(university.getCityId());
    final var universityEntity = UniversityEntity.builder()
        .name(university.getName())
        .city(cityEntity)
        .build();
    final var saved = universityPsqlDbRepository.save(universityEntity);
    return universityEntitiesMapper.fromUniversityEntity(saved);
  }

  @Override
  public Faculty create(final Faculty faculty) {
    final var universityEntity = universityPsqlDbRepository.getReferenceById(
        faculty.getUniversityId());

    final var savedFaculty = facultyPsqlDbRepository.save(FacultyEntity.builder()
        .name(faculty.getName())
        .university(universityEntity)
        .build());

    return universityEntitiesMapper.facultyFromEntity(savedFaculty);
  }

  @Override
  public List<Country> findAllCountries() {
    return universityEntitiesMapper.fromCountryEntity(countryPsqlDbRepository.findAll());
  }
}
