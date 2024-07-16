package com.ghsbm.group.peer.colab.domain.school.controller;

import com.ghsbm.group.peer.colab.domain.school.controller.model.CityDTO;
import com.ghsbm.group.peer.colab.domain.school.controller.model.CountryDTO;
import com.ghsbm.group.peer.colab.domain.school.controller.model.CreateFacultyRequest;
import com.ghsbm.group.peer.colab.domain.school.controller.model.CreateFacultyResponse;
import com.ghsbm.group.peer.colab.domain.school.controller.model.CreateUniversityRequest;
import com.ghsbm.group.peer.colab.domain.school.controller.model.CreateUniversityResponse;
import com.ghsbm.group.peer.colab.domain.school.controller.model.UniversityMapper;
import com.ghsbm.group.peer.colab.domain.school.core.ports.incoming.SchoolManagementService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/schools")
public class SchoolManagementController {

  @Autowired
  private SchoolManagementService schoolManagementService;

  @Autowired
  private UniversityMapper universityMapper;

  @GetMapping("/countries")
  public ResponseEntity<List<CountryDTO>> retrieveAllCountryNames() {
    return ResponseEntity.ok(
        universityMapper.countryDTOFrom(
            schoolManagementService.retrieveAllCountries()));
  }

  @GetMapping("/cities")
  public ResponseEntity<List<CityDTO>> retrieveCitiesByCountryId(
      final Long countryId) {
    Objects.requireNonNull(countryId);

    return ResponseEntity.ok(
        universityMapper.citiesDTOFrom(
            schoolManagementService.retrieveCityByCountryId(countryId)));
  }

  @PostMapping("/university")
  public ResponseEntity<CreateUniversityResponse> createUniversity(
      @RequestBody final CreateUniversityRequest createUniversityRequest) {
    Objects.requireNonNull(createUniversityRequest);
    Objects.requireNonNull(createUniversityRequest.getCityId());
    Objects.requireNonNull(createUniversityRequest.getName());

    final var university = schoolManagementService.createUniversity(
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

    final var faculty = schoolManagementService.createFaculty(
        universityMapper.fromCreateFacultyRequest(createFacultyRequest));

    return ResponseEntity.ok(CreateFacultyResponse.builder().id(faculty.getId()).build());
  }

  //todo create department
  //todo get universities by city id
  //todo get faculties by university id
  //todo get departments by faculty id


  //todo otpional: write unit/integration tests
}
