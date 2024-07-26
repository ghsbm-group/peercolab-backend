package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.school.core.model.City;
import com.ghsbm.group.peer.colab.domain.school.core.model.Country;
import com.ghsbm.group.peer.colab.domain.school.core.model.University;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
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

  private static University buildUniversityWithNullName() {
    return University.builder().cityId(CITY_ID).build();
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
  void retrieveCitiesByCountryIdShouldReturnValidList() {
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
}
