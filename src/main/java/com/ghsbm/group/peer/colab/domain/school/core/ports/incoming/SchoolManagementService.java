package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;


import com.ghsbm.group.peer.colab.domain.school.core.model.City;
import com.ghsbm.group.peer.colab.domain.school.core.model.Country;
import com.ghsbm.group.peer.colab.domain.school.core.model.Faculty;
import com.ghsbm.group.peer.colab.domain.school.core.model.University;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface SchoolManagementService {

  List<Country> retrieveAllCountries();

  List<City> retrieveCityByCountryId(Long countryId);

  @Transactional
  University createUniversity(University university);

  @Transactional
  Faculty createFaculty(Faculty faculty);
}
