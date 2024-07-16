package com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.school.core.model.City;
import com.ghsbm.group.peer.colab.domain.school.core.model.Country;
import com.ghsbm.group.peer.colab.domain.school.core.model.Faculty;
import com.ghsbm.group.peer.colab.domain.school.core.model.University;
import java.util.List;

public interface SchoolRepository {

  List<Country> findAllCountries();

  List<City> findCitiesByCountry(Long countryId);

  University create(University university);

  Faculty create(Faculty faculty);
}
