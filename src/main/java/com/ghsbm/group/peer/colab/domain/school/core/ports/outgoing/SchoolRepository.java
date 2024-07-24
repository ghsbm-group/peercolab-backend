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

  List<University> findUniversitiesByCity(Long cityId);

  List<Faculty> findFacultiesByUniversity(Long universityId);

  List<Department> findDepartmentsByFaculty(Long facultyId);

  University create(University university);

  Faculty create(Faculty faculty);

  Department create(Department department);
}
