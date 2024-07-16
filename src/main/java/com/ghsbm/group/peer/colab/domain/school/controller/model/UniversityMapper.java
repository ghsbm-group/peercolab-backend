package com.ghsbm.group.peer.colab.domain.school.controller.model;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;

import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityMapper {

  List<CountryDTO> countryDTOFrom(List<Country> countries);

  List<CityDTO> citiesDTOFrom(List<City> cities);

  List<UniversityDTO> universitiesDTOFrom(List<University> universities);

  List<FacultyDTO> facultiesDTOFrom(List<Faculty> faculties);

  List<DepartmentDTO> departmentsDTOFrom(List<Department> departments);

  University fromCreateUniversityRequest(CreateUniversityRequest createUniversityRequest);

  Faculty fromCreateFacultyRequest(CreateFacultyRequest facultyDTO);

  Department fromCreateDepartmentRequest(CreateDepartmentRequest departmentDTO);


}
