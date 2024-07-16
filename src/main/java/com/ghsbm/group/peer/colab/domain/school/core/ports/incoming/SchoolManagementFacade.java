package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;


import com.ghsbm.group.peer.colab.domain.school.core.model.City;
import com.ghsbm.group.peer.colab.domain.school.core.model.Country;
import com.ghsbm.group.peer.colab.domain.school.core.model.Faculty;
import com.ghsbm.group.peer.colab.domain.school.core.model.University;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolManagementFacade implements SchoolManagementService {

  @Autowired
  private SchoolRepository universityRepository;

  @Override
  public List<Country> retrieveAllCountries() {
    return universityRepository.findAllCountries();
  }

  @Override
  public List<City> retrieveCityByCountryId(Long countryId) {
    return universityRepository.findCitiesByCountry(countryId);
  }

  @Override
  public University createUniversity(University university) {
    return universityRepository.create(university);
  }

  @Override
  public Faculty createFaculty(Faculty faculty) {
    return universityRepository.create(faculty);
  }
}
