package com.ghsbm.group.peer.colab.domain.school.persistence.model;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UniversityEntitiesMapper {

  public abstract List<Country> fromCountryEntity(List<CountryEntity> all);

  public abstract List<City> fromCityEntities(List<CityEntity> cities);

  public abstract List<University> fromUniversityEntities(List<UniversityEntity> universities);

  public abstract List<Faculty> fromFacultyEntities(List<FacultyEntity> faculties);

  public abstract List<Department> fromDepartmentEntities(List<DepartmentEntity> departments);

  @Mapping(target = "cityId", source = "city.id")
  public abstract University fromUniversityEntity(UniversityEntity university);

  @Mapping(target = "universityId", source = "university.id")
  public abstract Faculty facultyFromEntity(FacultyEntity savedFaculty);

  @Mapping(target = "facultyId", source = "faculty.id")
  public abstract Department departmentFromEntity(DepartmentEntity savedDepartment);
}
