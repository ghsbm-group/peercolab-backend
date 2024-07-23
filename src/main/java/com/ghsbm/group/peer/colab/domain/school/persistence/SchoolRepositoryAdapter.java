package com.ghsbm.group.peer.colab.domain.school.persistence;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.*;
import com.ghsbm.group.peer.colab.domain.school.persistence.repository.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Implementation of the school repository interface. Reads and persists data into a db. */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class SchoolRepositoryAdapter implements SchoolRepository {

  @Autowired private CountryPsqlDbRepository countryPsqlDbRepository;

  @Autowired private CityPsqlDbRepository cityPsqlDbRepository;

  @Autowired private UniversityPsqlDbRepository universityPsqlDbRepository;

  @Autowired private FacultyPsqlDbRepository facultyPsqlDbRepository;

  @Autowired private DepartmentPsqlDbRepository departmentPsqlDbRepository;

  @Autowired private UniversityEntitiesMapper universityEntitiesMapper;

  @Override
  public List<City> findCitiesByCountry(Long countryId) {
    return universityEntitiesMapper.fromCityEntities(
        cityPsqlDbRepository.findByCountryId(countryId));
  }

  @Override
  public List<University> findUniversitiesByCity(Long cityId) {
    return universityEntitiesMapper.fromUniversityEntities(
        universityPsqlDbRepository.findByCityId(cityId));
  }

  @Override
  public List<Faculty> findFacultiesByUniversity(Long universityId) {
    return universityEntitiesMapper.fromFacultyEntities(
        facultyPsqlDbRepository.findByUniversityId(universityId));
  }

  @Override
  public List<Department> findDepartmentsByFaculty(Long facultyId) {
    return universityEntitiesMapper.fromDepartmentEntities(
        departmentPsqlDbRepository.findByFacultyId(facultyId));
  }

  @Override
  public University create(final University university) {
    final var cityEntity = cityPsqlDbRepository.getReferenceById(university.getCityId());
    final var universityEntity =
        UniversityEntity.builder().name(university.getName()).city(cityEntity).build();
    final var saved = universityPsqlDbRepository.save(universityEntity);
    return universityEntitiesMapper.fromUniversityEntity(saved);
  }

  @Override
  public Faculty create(final Faculty faculty) {
    final var universityEntity =
        universityPsqlDbRepository.getReferenceById(faculty.getUniversityId());

    final var savedFaculty =
        facultyPsqlDbRepository.save(
            FacultyEntity.builder().name(faculty.getName()).university(universityEntity).build());

    return universityEntitiesMapper.facultyFromEntity(savedFaculty);
  }

  @Override
  public Department create(final Department department) {
    final var facultyEntity = facultyPsqlDbRepository.getReferenceById(department.getFacultyId());
    final var savedDepartment =
        departmentPsqlDbRepository.save(
            DepartmentEntity.builder().name(department.getName()).faculty(facultyEntity).build());
    return universityEntitiesMapper.departmentFromEntity(savedDepartment);
  }
  /**
   * @inheritDoc
   */
  @Override
  public List<Country> findAllCountries() {
    return universityEntitiesMapper.fromCountryEntity(countryPsqlDbRepository.findAll());
  }
}
