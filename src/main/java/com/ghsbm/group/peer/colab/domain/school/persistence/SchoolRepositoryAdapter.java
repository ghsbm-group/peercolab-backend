package com.ghsbm.group.peer.colab.domain.school.persistence;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.*;
import com.ghsbm.group.peer.colab.domain.school.persistence.repository.*;

import java.util.List;
import java.util.Optional;

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

  @Autowired private CountryPsqlDbRepository countryPsqlDbRepository;

  @Autowired private CityPsqlDbRepository cityPsqlDbRepository;

  @Autowired private UniversityPsqlDbRepository universityPsqlDbRepository;

  @Autowired private FacultyPsqlDbRepository facultyPsqlDbRepository;

  @Autowired private DepartmentPsqlDbRepository departmentPsqlDbRepository;

  @Autowired private ClassPsqlDbRepository classPsqlDbRepository;

  @Autowired private FolderPsqlDbRespository folderPsqlDbRespository;

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
  public List<ClassConfiguration> findClassesByDepartment(Long departmentId) {
    return universityEntitiesMapper.fromClassEntities(
        classPsqlDbRepository.findByDepartmentId(departmentId));
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

  @Override
  public ClassConfiguration create(ClassConfiguration classConfigurationInfo) {
    final var departmentEntity =
        departmentPsqlDbRepository.getReferenceById(classConfigurationInfo.getDepartmentId());
    final var savedClass =
        classPsqlDbRepository.save(
            ClassConfigurationEntity.builder()
                .name(classConfigurationInfo.getName())
                .startYear(classConfigurationInfo.getStartYear())
                .noOfStudyYears(classConfigurationInfo.getNoOfStudyYears())
                .noOfSemestersPerYear(classConfigurationInfo.getNoOfSemestersPerYear())
                .department(departmentEntity)
                .build());
    return universityEntitiesMapper.classFromEntity(savedClass);
  }

  @Override
  public Folder create(Folder folder) {
    final var classEntity =
        classPsqlDbRepository.getReferenceById(folder.getClassConfigurationId());
    var folderEntity = Optional.<FolderEntity>empty();
    if (folder.getParentId() != null) {
      folderEntity = folderPsqlDbRespository.findById(folder.getParentId());
    }
    final var savedFolder =
        folderPsqlDbRespository.save(
            FolderEntity.builder()
                .name(folder.getName())
                .classConfiguration(classEntity)
                .parent(folderEntity.orElse(null))
                .build());

    return universityEntitiesMapper.folderFromEntity(savedFolder);
  }

  @Override
  public List<Country> findAllCountries() {
    return universityEntitiesMapper.fromCountryEntity(countryPsqlDbRepository.findAll());
  }
}
