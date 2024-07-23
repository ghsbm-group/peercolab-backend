package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import java.util.List;
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

  List<City> retrieveCityByCountryId(Long countryId);

  List<University> retrieveUniversityByCityId(Long cityId);

  List<Faculty> retrieveFacultyByUniversityId(Long universityId);

  List<Department> retrieveDepartmentByFacultyId(Long facultyId);

  @Transactional
  University createUniversity(University university);

  @Transactional
  Faculty createFaculty(Faculty faculty);

  @Transactional
  Department createDepartment(Department department);
}
