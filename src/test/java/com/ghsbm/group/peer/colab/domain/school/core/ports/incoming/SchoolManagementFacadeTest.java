package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.school.core.model.City;
import com.ghsbm.group.peer.colab.domain.school.core.model.Country;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.CityEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SchoolManagementFacadeTest {
  public static final String COUNTRY_NAME = "CountyName";

  public static final String CITY_NAME = "CityName";
  public static final long COUNTRY_ID = 1L;
  public static final long CITY_ID=1L;
  @InjectMocks private SchoolManagementFacade victim;
  @Mock private SchoolRepository schoolRepository;

  private static Country buildValidCountry() {
    return Country.builder().id(COUNTRY_ID).name(COUNTRY_NAME).build();
  }
  private static City buildValidCity(){
    return City.builder()
            .id(CITY_ID)
            .name(CITY_NAME)
            .build();
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
  void retrieveCitiesByCountryIdShouldReturnValidList(){
    List<City> expectedReturnValue=List.of(buildValidCity());
    when(schoolRepository.findCitiesByCountry(COUNTRY_ID)).thenReturn(expectedReturnValue);

    List<City> response = victim.retrieveCityByCountryId(COUNTRY_ID);

    assertEquals(expectedReturnValue, response);
    verify(schoolRepository,times(1)).findCitiesByCountry(COUNTRY_ID);
  }
}
