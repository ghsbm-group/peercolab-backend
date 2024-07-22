package com.ghsbm.group.peer.colab.domain.school.controller;

import com.ghsbm.group.peer.colab.domain.school.controller.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.ports.incoming.SchoolManagementService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schools")
public class SchoolManagementController {

  @Autowired private SchoolManagementService schoolManagementService;

  @Autowired private UniversityMapper universityMapper;

  @GetMapping("/countries")
  public ResponseEntity<List<CountryDTO>> retrieveAllCountryNames() {
    return ResponseEntity.ok(
        universityMapper.countryDTOFrom(schoolManagementService.retrieveAllCountries()));
  }

  @GetMapping("/cities")
  public ResponseEntity<List<CityDTO>> retrieveCitiesByCountryId(final Long countryId) {
    Objects.requireNonNull(countryId);

    return ResponseEntity.ok(
        universityMapper.citiesDTOFrom(schoolManagementService.retrieveCityByCountryId(countryId)));
  }

  @PostMapping("/university")
  public ResponseEntity<CreateUniversityResponse> createUniversity(
      @RequestBody final CreateUniversityRequest createUniversityRequest) {
    Objects.requireNonNull(createUniversityRequest);
    Objects.requireNonNull(createUniversityRequest.getCityId());
    Objects.requireNonNull(createUniversityRequest.getName());

    final var university =
        schoolManagementService.createUniversity(
            universityMapper.fromCreateUniversityRequest(createUniversityRequest));
    return ResponseEntity.ok(
        CreateUniversityResponse.builder().universityId(university.getId()).build());
  }

  @PostMapping("/faculty")
  public ResponseEntity<CreateFacultyResponse> createFaculty(
      @RequestBody final CreateFacultyRequest createFacultyRequest) {
    Objects.requireNonNull(createFacultyRequest);
    Objects.requireNonNull(createFacultyRequest.getUniversityId());
    Objects.requireNonNull(createFacultyRequest.getName());

    final var faculty =
        schoolManagementService.createFaculty(
            universityMapper.fromCreateFacultyRequest(createFacultyRequest));

    return ResponseEntity.ok(CreateFacultyResponse.builder().id(faculty.getId()).build());
  }

  @PostMapping("/department")
  public ResponseEntity<CreateDepartmentResponse> createDepartment(
      @RequestBody final CreateDepartmentRequest createDepartmentRequest) {
    Objects.requireNonNull(createDepartmentRequest);
    Objects.requireNonNull(createDepartmentRequest.getFacultyId());
    Objects.requireNonNull(createDepartmentRequest.getName());

    final var department =
        schoolManagementService.createDepartment(
            universityMapper.fromCreateDepartmentRequest(createDepartmentRequest));

    return ResponseEntity.ok(CreateDepartmentResponse.builder().id(department.getId()).build());
  }

  @GetMapping("/universities")
  public ResponseEntity<List<UniversityDTO>> retrieveUniversitiesByCityId(final Long cityId) {
    Objects.requireNonNull(cityId);

    return ResponseEntity.ok(
        universityMapper.universitiesDTOFrom(
            schoolManagementService.retrieveUniversityByCityId(cityId)));
  }

  @GetMapping("/faculties")
  public ResponseEntity<List<FacultyDTO>> retrieveFacultiesByUniversityId(final Long universityId) {
    Objects.requireNonNull(universityId);

    return ResponseEntity.ok(
        universityMapper.facultiesDTOFrom(
            schoolManagementService.retrieveFacultyByUniversityId(universityId)));
  }

  @GetMapping("/departments")
  public ResponseEntity<List<DepartmentDTO>> retrieveDepartmentsByFacultyId(final Long facultyId) {
    Objects.requireNonNull(facultyId);

    return ResponseEntity.ok(
        universityMapper.departmentsDTOFrom(
            schoolManagementService.retrieveDepartmentByFacultyId(facultyId)));
  }
}
