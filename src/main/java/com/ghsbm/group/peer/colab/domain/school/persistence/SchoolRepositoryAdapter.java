package com.ghsbm.group.peer.colab.domain.school.persistence;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.*;
import com.ghsbm.group.peer.colab.domain.school.persistence.repository.*;
import java.util.List;
import lombok.Setter;
import org.springframework.stereotype.Component;

/** Implementation of the school repository interface. Reads and persists data into a db. */
@Component
@Setter
public class SchoolRepositoryAdapter implements SchoolRepository {

  private final CountryPsqlDbRepository countryPsqlDbRepository;
  private final CityPsqlDbRepository cityPsqlDbRepository;
  private final UniversityPsqlDbRepository universityPsqlDbRepository;
  private final FacultyPsqlDbRepository facultyPsqlDbRepository;
  private final DepartmentPsqlDbRepository departmentPsqlDbRepository;
  private final UniversityEntitiesMapper universityEntitiesMapper;

  public SchoolRepositoryAdapter(
      CountryPsqlDbRepository countryPsqlDbRepository,
      CityPsqlDbRepository cityPsqlDbRepository,
      UniversityPsqlDbRepository universityPsqlDbRepository,
      FacultyPsqlDbRepository facultyPsqlDbRepository,
      DepartmentPsqlDbRepository departmentPsqlDbRepository,
      UniversityEntitiesMapper universityEntitiesMapper) {
    this.countryPsqlDbRepository = countryPsqlDbRepository;
    this.cityPsqlDbRepository = cityPsqlDbRepository;
    this.universityPsqlDbRepository = universityPsqlDbRepository;
    this.facultyPsqlDbRepository = facultyPsqlDbRepository;
    this.departmentPsqlDbRepository = departmentPsqlDbRepository;
    this.universityEntitiesMapper = universityEntitiesMapper;
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<City> findCitiesByCountry(Long countryId) {
    return universityEntitiesMapper.fromCityEntities(
        cityPsqlDbRepository.findByCountryIdOrderByName(countryId));
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<University> findUniversitiesByCity(Long cityId) {
    return universityEntitiesMapper.fromUniversityEntities(
        universityPsqlDbRepository.findByCityIdOrderByName(cityId));
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Faculty> findFacultiesByUniversity(Long universityId) {
    return universityEntitiesMapper.fromFacultyEntities(
        facultyPsqlDbRepository.findByUniversityIdOrderByName(universityId));
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Department> findDepartmentsByFaculty(Long facultyId) {
    return universityEntitiesMapper.fromDepartmentEntities(
        departmentPsqlDbRepository.findByFacultyId(facultyId));
  }

  /**
   * @inheritDoc
   */
  @Override
  public University create(final University university) {
    final var cityEntity = cityPsqlDbRepository.getReferenceById(university.getCityId());
    final var universityEntity =
        UniversityEntity.builder().name(university.getName()).city(cityEntity).build();
    final var saved = universityPsqlDbRepository.save(universityEntity);
    return universityEntitiesMapper.fromUniversityEntity(saved);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Faculty create(final Faculty faculty) {
    final var universityEntity =
        universityPsqlDbRepository.getReferenceById(faculty.getUniversityId());

    final var savedFaculty =
        facultyPsqlDbRepository.save(
            FacultyEntity.builder().name(faculty.getName()).university(universityEntity).build());

    return universityEntitiesMapper.facultyFromEntity(savedFaculty);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Department create(final Department department) {
    final var facultyEntity = facultyPsqlDbRepository.getReferenceById(department.getFacultyId());
    final var savedDepartment =
        departmentPsqlDbRepository.save(
            DepartmentEntity.builder().name(department.getName()).faculty(facultyEntity).build());
    return universityEntitiesMapper.departmentFromEntity(savedDepartment);
  }

  @Override
  public ClassParentDetails getDetailsByDepartmentId(Long departmentId) {
    DepartmentEntity departmentEntity = departmentPsqlDbRepository.getReferenceById(departmentId);
    FacultyEntity faculty = departmentEntity.getFaculty();
    UniversityEntity university = faculty.getUniversity();
    CityEntity city = university.getCity();
    CountryEntity country = city.getCountry();
    return ClassParentDetails.builder()
        .department(universityEntitiesMapper.departmentFromEntity(departmentEntity))
        .faculty(universityEntitiesMapper.facultyFromEntity(faculty))
        .university(universityEntitiesMapper.fromUniversityEntity(university))
        .country(universityEntitiesMapper.fromCountryEntity(country))
        .city(universityEntitiesMapper.fromCityEntity(city))
        .build();
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Country> findAllCountries() {
    return universityEntitiesMapper.fromCountryEntites(countryPsqlDbRepository.findAll());
  }
}
