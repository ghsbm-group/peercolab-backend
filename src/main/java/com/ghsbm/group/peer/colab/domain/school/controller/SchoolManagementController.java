package com.ghsbm.group.peer.colab.domain.school.controller;

import com.ghsbm.group.peer.colab.domain.school.controller.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.ports.incoming.SchoolManagementService;
import java.util.List;
import java.util.Objects;

import com.ghsbm.group.peer.colab.domain.school.exceptions.ApiExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schools")
public class SchoolManagementController {

  @Autowired private SchoolManagementService schoolManagementService;

  @Autowired private UniversityMapper universityMapper;

  /**
   * Returns information about all countries
   *
   * @return A list of {@link CountryDTO} encapsulating data about countries.
   */
  @GetMapping("/countries")
  public ResponseEntity<List<CountryDTO>> retrieveAllCountryNames() {
    return ResponseEntity.ok(
        universityMapper.countryDTOFrom(schoolManagementService.retrieveAllCountries()));
  }

  /**
   * Returns information about cities that are part of a specific country.
   *
   * @param countryId The country identifier for which the list of cities will be returned.
   * @return A list of {@link CityDTO} encapsulating data about cities.
   */
  @GetMapping("/cities")
  public ResponseEntity<List<CityDTO>> retrieveCitiesByCountryId(final Long countryId) throws ApiExceptionResponse {
    Objects.requireNonNull(countryId);
    return ResponseEntity.ok(
        universityMapper.citiesDTOFrom(schoolManagementService.retrieveCityByCountryId(countryId)));
  }

  /**
   * Endpoint for creating a new university.
   *
   * @param createUniversityRequest {@link CreateUniversityRequest} encapsulates the university
   *     configuration parameters.
   * @return a {@link CreateUniversityResponse} containing the configuration identifiers for the
   *     created university.
   */
  @PostMapping("/university")
  public ResponseEntity<CreateUniversityResponse> createUniversity(
      @RequestBody final CreateUniversityRequest createUniversityRequest) {

    final var university =
        schoolManagementService.createUniversity(
            universityMapper.fromCreateUniversityRequest(createUniversityRequest));
    return ResponseEntity.ok(
        CreateUniversityResponse.builder().universityId(university.getId()).build());
  }

  /**
   * Endpoint for creating a new faculty.
   *
   * @param createFacultyRequest {@link CreateFacultyRequest} encapsulates the faculty configuration
   *     parameters.
   * @return a {@link CreateFacultyResponse} containing the configuration identifiers for the
   *     created faculty.
   */
  @PostMapping("/faculty")
  public ResponseEntity<CreateFacultyResponse> createFaculty(
      @RequestBody final CreateFacultyRequest createFacultyRequest) {

    final var faculty =
        schoolManagementService.createFaculty(
            universityMapper.fromCreateFacultyRequest(createFacultyRequest));

    return ResponseEntity.ok(CreateFacultyResponse.builder().id(faculty.getId()).build());
  }

  /**
   * Endpoint for creating a new department.
   *
   * @param createDepartmentRequest {@link CreateDepartmentRequest} encapsulates the department
   *     configuration parameters.
   * @return a {@link CreateDepartmentResponse} containing the configuration identifiers for the
   *     created department.
   */
  @PostMapping("/department")
  public ResponseEntity<CreateDepartmentResponse> createDepartment(
      @RequestBody final CreateDepartmentRequest createDepartmentRequest) {

    final var department =
        schoolManagementService.createDepartment(
            universityMapper.fromCreateDepartmentRequest(createDepartmentRequest));

    return ResponseEntity.ok(CreateDepartmentResponse.builder().id(department.getId()).build());
  }

  /**
   * Returns information about universities that are part of a specific city.
   *
   * @param cityId The city identifier for which the list of universities will be returned.
   * @return A list of {@link UniversityDTO} encapsulating data about universities.
   */
  @GetMapping("/universities")
  public ResponseEntity<List<UniversityDTO>> retrieveUniversitiesByCityId(final Long cityId) {
    Objects.requireNonNull(cityId);

    return ResponseEntity.ok(
        universityMapper.universitiesDTOFrom(
            schoolManagementService.retrieveUniversityByCityId(cityId)));
  }

  /**
   * Returns information about faculties that are part of a specific university.
   *
   * @param universityId The university identifier for which the list of faculties will be returned.
   * @return A list of {@link FacultyDTO} encapsulating data about faculties.
   */
  @GetMapping("/faculties")
  public ResponseEntity<List<FacultyDTO>> retrieveFacultiesByUniversityId(final Long universityId) {
    Objects.requireNonNull(universityId);

    return ResponseEntity.ok(
        universityMapper.facultiesDTOFrom(
            schoolManagementService.retrieveFacultyByUniversityId(universityId)));
  }

  /**
   * Returns information about departments that are part of a specific faculty.
   *
   * @param facultyId The faculty identifier for which the list of departments will be returned.
   * @return A list of {@link DepartmentDTO} encapsulating data about departments.
   */
  @GetMapping("/departments")
  public ResponseEntity<List<DepartmentDTO>> retrieveDepartmentsByFacultyId(final Long facultyId) {
    Objects.requireNonNull(facultyId);

    return ResponseEntity.ok(
        universityMapper.departmentsDTOFrom(
            schoolManagementService.retrieveDepartmentByFacultyId(facultyId)));
  }
}
