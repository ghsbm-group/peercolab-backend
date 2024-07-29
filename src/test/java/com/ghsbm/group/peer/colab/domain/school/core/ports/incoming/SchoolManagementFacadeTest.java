package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
import com.ghsbm.group.peer.colab.domain.school.exceptions.ApiExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SchoolManagementFacadeTest {
  public static final String UNIVERSITY_NAME = "UniversityName";
  public static final String COUNTRY_NAME = "CountyName";
  public static final String CITY_NAME = "CityName";
  public static final String FACULTY_NAME = "FacultyName";
  public static final String DEPARTMENT_NAME = "DepartmentName";
  public static final long DEPARTMENT_ID = 1L;
  public static final long FACULTY_ID = 1L;
  public static final long COUNTRY_ID = 1L;
  public static final long CITY_ID = 1L;
  public static final long UNIVERSITY_ID = 1L;
  @InjectMocks private SchoolManagementFacade victim;
  @Mock private SchoolRepository schoolRepository;

  private static Country buildValidCountry() {
    return Country.builder().id(COUNTRY_ID).name(COUNTRY_NAME).build();
  }

  private static City buildValidCity() {
    return City.builder().id(CITY_ID).name(CITY_NAME).build();
  }

  private static University buildValidUniversity() {
    return University.builder().name(UNIVERSITY_NAME).cityId(CITY_ID).build();
  }

  private static Faculty buildValidFaculty() {
    return Faculty.builder().name(FACULTY_NAME).universityId(UNIVERSITY_ID).build();
  }

  private static Department buildValidDepartment() {
    return Department.builder().name(DEPARTMENT_NAME).facultyId(FACULTY_ID).build();
  }

  private static University buildUniversityWithNullName() {
    return University.builder().cityId(CITY_ID).build();
  }

  private static Faculty buildFacultyWithNullName() {
    return Faculty.builder().universityId(UNIVERSITY_ID).build();
  }

  private static Department buildDepartmentWithNullName() {
    return Department.builder().facultyId(FACULTY_ID).build();
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void retrieveCountriesShouldReturnAValidList() {
    List<Country> expectedReturnValue = List.of(buildValidCountry());
    when(schoolRepository.findAllCountries()).thenReturn(expectedReturnValue);

    List<Country> response = victim.retrieveAllCountries();

    assertEquals(expectedReturnValue, response);
  }

  @Test
  void retrieveCitiesByCountryIdShouldReturnValidList() throws ApiExceptionResponse {
    List<City> expectedReturnValue = List.of(buildValidCity());
    when(schoolRepository.findCitiesByCountry(COUNTRY_ID)).thenReturn(expectedReturnValue);

    List<City> response = victim.retrieveCityByCountryId(COUNTRY_ID);

    assertEquals(expectedReturnValue, response);
    verify(schoolRepository, times(1)).findCitiesByCountry(COUNTRY_ID);
  }

  @Test
  void createdUniversityShouldHaveTheIdSet() {
    University toBeCreated = buildValidUniversity();
    when(schoolRepository.create(toBeCreated))
        .thenReturn(
            University.builder().id(UNIVERSITY_ID).name(UNIVERSITY_NAME).cityId(CITY_ID).build());

    // when
    University createdUniversity = victim.createUniversity(toBeCreated);

    // then
    assertEquals(toBeCreated.getName(), createdUniversity.getName());
    assertEquals(toBeCreated.getCityId(), createdUniversity.getCityId());
    assertEquals(UNIVERSITY_ID, createdUniversity.getId());
  }

  @Test
  void createUniversityShouldThrowExceptionForInvalidUniversity() {
    assertThrows(
        NullPointerException.class, () -> victim.createUniversity(buildUniversityWithNullName()));
    assertThrows(NullPointerException.class, () -> victim.createUniversity(null));
  }

  @Test
  void retrieveUniversitiesByCityIdShouldReturnAValidList() {
    List<University> expectedReturnValue = List.of(buildValidUniversity());
    when(schoolRepository.findUniversitiesByCity(CITY_ID)).thenReturn(expectedReturnValue);

    List<University> response = victim.retrieveUniversityByCityId(CITY_ID);

    assertEquals(expectedReturnValue, response);
  }

  @Test
  void createdFacultyShouldHaveTheIdSet() {
    Faculty toBeCreated = buildValidFaculty();
    when(schoolRepository.create(toBeCreated))
        .thenReturn(
            Faculty.builder()
                .id(FACULTY_ID)
                .name(FACULTY_NAME)
                .universityId(UNIVERSITY_ID)
                .build());

    // when
    Faculty createdFaculty = victim.createFaculty(toBeCreated);

    // then
    assertEquals(toBeCreated.getName(), createdFaculty.getName());
    assertEquals(toBeCreated.getUniversityId(), createdFaculty.getUniversityId());
    assertEquals(UNIVERSITY_ID, createdFaculty.getId());
  }

  @Test
  void createFacultyShouldThrowExceptionForInvalidFaculty() {
    assertThrows(
        NullPointerException.class, () -> victim.createFaculty(buildFacultyWithNullName()));
    assertThrows(NullPointerException.class, () -> victim.createFaculty(null));
  }

  @Test
  void retrieveFacultiesByUniversityIdShouldReturnAValidList() {
    List<Faculty> expectedReturnValue = List.of(buildValidFaculty());
    when(schoolRepository.findFacultiesByUniversity(UNIVERSITY_ID)).thenReturn(expectedReturnValue);

    List<Faculty> response = victim.retrieveFacultyByUniversityId(UNIVERSITY_ID);

    assertEquals(expectedReturnValue, response);
  }

  @Test
  void createdDepartmentShouldHaveTheIdSet() {
    Department toBeCreated = buildValidDepartment();
    when(schoolRepository.create(toBeCreated))
        .thenReturn(
            Department.builder()
                .id(DEPARTMENT_ID)
                .name(DEPARTMENT_NAME)
                .facultyId(FACULTY_ID)
                .build());

    // when
    Department createdDepartment = victim.createDepartment(toBeCreated);

    // then
    assertEquals(toBeCreated.getName(), createdDepartment.getName());
    assertEquals(toBeCreated.getFacultyId(), createdDepartment.getFacultyId());
    assertEquals(FACULTY_ID, createdDepartment.getId());
  }

  @Test
  void createdDepartmentShouldThrowExceptionForInvalidDepartment() {
    assertThrows(
        NullPointerException.class, () -> victim.createDepartment(buildDepartmentWithNullName()));
    assertThrows(NullPointerException.class, () -> victim.createDepartment(null));
  }

  @Test
  void retrieveDepartmentsByFacultyIdShouldReturnAValidList() {
    List<Department> expectedReturnValue = List.of(buildValidDepartment());
    when(schoolRepository.findDepartmentsByFaculty(FACULTY_ID)).thenReturn(expectedReturnValue);

    List<Department> response = victim.retrieveDepartmentByFacultyId(FACULTY_ID);

    assertEquals(expectedReturnValue, response);
  }
}
