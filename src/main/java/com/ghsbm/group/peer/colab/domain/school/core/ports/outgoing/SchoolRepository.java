package com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import java.util.List;

/**
 * Interface contract between core business logic and the persistence layer.
 *
 * <p>Contains methods for persisting and reading data.
 */
public interface SchoolRepository {
  /**
   * Retrieves all countries
   *
   * @return a list of {@link Country} entities.
   */
  List<Country> findAllCountries();

  /**
   * Retrieves all cities belonging to this country.
   *
   * @param countryId The country id for which the cities are returned.
   * @return a list of {@link City} entities.
   */
  List<City> findCitiesByCountry(Long countryId);

  /**
   * Retrieves all universities belonging to this city.
   *
   * @param cityId The city id for which the universities are returned.
   * @return a list of {@link University} entities.
   */
  List<University> findUniversitiesByCity(Long cityId);

  /**
   * Retrieves all faculties belonging to this university.
   *
   * @param universityId The university id for which the faculties are returned.
   * @return a list of {@link Faculty} entities.
   */
  List<Faculty> findFacultiesByUniversity(Long universityId);

  /**
   * Retrieves all departments belonging to this faculty.
   *
   * @param facultyId The faculty id for which the departments are returned.
   * @return a list of {@link Department} entities.
   */
  List<Department> findDepartmentsByFaculty(Long facultyId);

  /**
   * Persists a university.
   *
   * @param university the university configuration to be persisted.
   * @return A {@link University} with the id set.
   */
  University create(University university);

  /**
   * Persists a faculty.
   *
   * @param faculty the faculty configuration to be persisted.
   * @return A {@link Faculty} with the id set.
   */
  Faculty create(Faculty faculty);

  /**
   * Persists a department.
   *
   * @param department the department configuration to be persisted.
   * @return A {@link Department} with the id set.
   */
  Department create(Department department);

  /**
   * Returns info about the university, faculty, city and country of this depratment.
   *
   * @param departmentId the department for which the info is retrieved.
   * @return a {@Link ClassParentDetails} object containing all the info.
   */
  ClassParentDetails getDetailsByDepartmentId(Long departmentId);
}
