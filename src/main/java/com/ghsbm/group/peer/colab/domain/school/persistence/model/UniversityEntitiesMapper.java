package com.ghsbm.group.peer.colab.domain.school.persistence.model;

import com.ghsbm.group.peer.colab.domain.school.core.model.City;
import com.ghsbm.group.peer.colab.domain.school.core.model.Country;
import com.ghsbm.group.peer.colab.domain.school.core.model.Faculty;
import com.ghsbm.group.peer.colab.domain.school.core.model.University;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UniversityEntitiesMapper {

  public abstract List<Country> fromCountryEntity(List<CountryEntity> all);

  public abstract List<City> fromCityEntities(List<CityEntity> cities);

  @Mapping(target = "cityId", source = "city.id")
  public abstract University fromUniversityEntity(UniversityEntity university);

  @Mapping(target = "universityId", source = "university.id")
  public abstract Faculty facultyFromEntity(FacultyEntity savedFaculty);
}
