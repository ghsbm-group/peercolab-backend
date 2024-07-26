package com.ghsbm.group.peer.colab.domain.school.controller.model;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityMapper {
  /**
   * Transforms a list of {@link Country} into a list of {@link CountryDTO}.
   *
   * @param countries the list of {@link Country} objects to be transformed.
   * @return a list of {@link CountryDTO} matching the parameter passed to this method.
   */
  List<CountryDTO> countryDTOFrom(List<Country> countries);

  /**
   * Transforms a list of {@link City} into a list of {@link CityDTO}.
   *
   * @param cities the list of {@link City} objects to be transformed.
   * @return a list of {@link CityDTO} matching the parameter passed to this method.
   */
  List<CityDTO> citiesDTOFrom(List<City> cities);
  /**
   * Transforms a list of {@link University} into a list of {@link UniversityDTO}.
   *
   * @param universities the list of {@link University} objects to be transformed.
   * @return a list of {@link UniversityDTO} matching the parameter passed to this method.
   */
  List<UniversityDTO> universitiesDTOFrom(List<University> universities);
  /**
   * Transforms a list of {@link Faculty} into a list of {@link FacultyDTO}.
   *
   * @param faculties the list of {@link Faculty} objects to be transformed.
   * @return a list of {@link FacultyDTO} matching the parameter passed to this method.
   */
  List<FacultyDTO> facultiesDTOFrom(List<Faculty> faculties);
  /**
   * Transforms a list of {@link Department} into a list of {@link DepartmentDTO}.
   *
   * @param departments the list of {@link Department} objects to be transformed.
   * @return a list of {@link DepartmentDTO} matching the parameter passed to this method.
   */
  List<DepartmentDTO> departmentsDTOFrom(List<Department> departments);
  /**
   * Builds a {@link University} core domain object based on a {@link CreateUniversityRequest} instance.
   *
   * @param createUniversityRequest encapsulates the requests parameters.
   * @return a {@link University} object build based on the request parameter.
   */
  University fromCreateUniversityRequest(CreateUniversityRequest createUniversityRequest);
  /**
   * Builds a {@link Faculty} core domain object based on a {@link CreateFacultyRequest} instance.
   *
   * @param facultyDTO encapsulates the requests parameters.
   * @return a {@link Faculty} object build based on the request parameter.
   */
  Faculty fromCreateFacultyRequest(CreateFacultyRequest facultyDTO);
  /**
   * Builds a {@link Department} core domain object based on a {@link CreateDepartmentRequest} instance.
   *
   * @param departmentDTO encapsulates the requests parameters.
   * @return a {@link Department} object build based on the request parameter.
   */
  Department fromCreateDepartmentRequest(CreateDepartmentRequest departmentDTO);
}
