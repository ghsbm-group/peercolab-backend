package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import java.util.List;

import com.ghsbm.group.peer.colab.domain.school.exceptions.ApiExceptionResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface contract between the API and the core business logic.
 *
 * <p>Contains methods used for the management of school.
 */
public interface SchoolManagementService {

  /**
   * Retrieves all countries.
   *
   * @return a list of {@link Country}
   */
  List<Country> retrieveAllCountries();

  /**
   * Retrieves all cities associated with a country. If the countryId is not found, an {@link
   * ApiExceptionResponse} is thrown.
   *
   * @return a list of {@link City}
   * @throws ApiExceptionResponse if the countryId is not found
   */
  List<City> retrieveCityByCountryId(Long countryId) throws ApiExceptionResponse;

  /**
   * Retrieves all universities associated with a city.
   *
   * @return a list of {@link University}
   */
  List<University> retrieveUniversityByCityId(Long cityId);

  /**
   * Retrieves all faculties associated with a university.
   *
   * @return a list of {@link Faculty}
   */
  List<Faculty> retrieveFacultyByUniversityId(Long universityId);

  /**
   * Retrieves all departments associated with a faculty.
   *
   * @return a list of {@link Department}
   */
  List<Department> retrieveDepartmentByFacultyId(Long facultyId);

  /**
   * Persists the university.
   *
   * @param university encapsulates university data.
   * @return a {@link University} object with the id attribute set.
   */
  @Transactional
  University createUniversity(University university);

  /**
   * Persists the faculty.
   *
   * @param faculty encapsulates faculty data.
   * @return a {@link Faculty} object with the id attribute set.
   */
  @Transactional
  Faculty createFaculty(Faculty faculty);

  /**
   * Persists the department.
   *
   * @param department encapsulates department data.
   * @return a {@link Department} object with the id attribute set.
   */
  @Transactional
  Department createDepartment(Department department);
}
