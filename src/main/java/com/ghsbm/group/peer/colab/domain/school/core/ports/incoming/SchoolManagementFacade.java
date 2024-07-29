package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.ghsbm.group.peer.colab.domain.school.exceptions.ApiExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** Service that contains the core business logic. */
@Service
public class SchoolManagementFacade implements SchoolManagementService {

  @Autowired private SchoolRepository universityRepository;

  /**
   * @inheritDoc
   */
  @Override
  public List<Country> retrieveAllCountries() {
    return universityRepository.findAllCountries();
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<City> retrieveCityByCountryId(Long countryId) throws ApiExceptionResponse {
    List<City> cities = universityRepository.findCitiesByCountry(countryId);
    if(cities.isEmpty())
    {
      throw ApiExceptionResponse.builder()
          .errors(Collections.singletonList("No cities were found for countryId: " + countryId))
          .message("Entities not found")
          .status(HttpStatus.BAD_REQUEST)
          .build();
    }
    return cities;
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<University> retrieveUniversityByCityId(Long cityId) {
    return universityRepository.findUniversitiesByCity(cityId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Faculty> retrieveFacultyByUniversityId(Long universityId) {
    return universityRepository.findFacultiesByUniversity(universityId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Department> retrieveDepartmentByFacultyId(Long facultyId) {
    return universityRepository.findDepartmentsByFaculty(facultyId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public University createUniversity(University university) {
    Objects.requireNonNull(university);
    Objects.requireNonNull(university.getCityId());
    Objects.requireNonNull(university.getName());
    return universityRepository.create(university);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Faculty createFaculty(Faculty faculty) {
    Objects.requireNonNull(faculty);
    Objects.requireNonNull(faculty.getUniversityId());
    Objects.requireNonNull(faculty.getName());
    return universityRepository.create(faculty);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Department createDepartment(Department department) {
    Objects.requireNonNull(department);
    Objects.requireNonNull(department.getFacultyId());
    Objects.requireNonNull(department.getName());
    return universityRepository.create(department);
  }
}
