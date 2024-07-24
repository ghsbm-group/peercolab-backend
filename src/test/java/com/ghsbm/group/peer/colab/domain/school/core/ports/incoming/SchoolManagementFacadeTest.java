package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.school.core.model.Country;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SchoolManagementFacadeTest {
  public static final String COUNTRY_NAME = "CountyName";
  public static final long COUNTRY_ID = 1L;
  @InjectMocks private SchoolManagementFacade victim;
  @Mock private SchoolRepository schoolRepository;

  private static Country buildValidCountry() {
    return Country.builder().id(COUNTRY_ID).name(COUNTRY_NAME).build();
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
}
