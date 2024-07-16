package com.ghsbm.group.peer.colab.domain.school.controller.model;

import com.ghsbm.group.peer.colab.domain.school.core.model.City;
import com.ghsbm.group.peer.colab.domain.school.core.model.Country;
import com.ghsbm.group.peer.colab.domain.school.core.model.Faculty;
import com.ghsbm.group.peer.colab.domain.school.core.model.University;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityMapper {

  List<CountryDTO> countryDTOFrom(List<Country> countries);

  List<CityDTO> citiesDTOFrom(List<City> cities);

  University fromCreateUniversityRequest(CreateUniversityRequest createUniversityRequest);

  Faculty fromCreateFacultyRequest(CreateFacultyRequest facultyDTO);
}
