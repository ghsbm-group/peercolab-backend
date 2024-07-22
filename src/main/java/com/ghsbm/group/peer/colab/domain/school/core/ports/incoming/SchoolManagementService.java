package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import java.util.List;

import com.ghsbm.group.peer.colab.domain.school.core.model.ClassConfiguration;
import org.springframework.transaction.annotation.Transactional;

public interface SchoolManagementService {

  List<Country> retrieveAllCountries();

  List<City> retrieveCityByCountryId(Long countryId);

  List<University> retrieveUniversityByCityId(Long cityId);

  List<Faculty> retrieveFacultyByUniversityId(Long universityId);

  List<Department> retrieveDepartmentByFacultyId(Long facultyId);

  List<ClassConfiguration> retrieveClassByDepartmentId(Long classId);

  @Transactional
  University createUniversity(University university);

  @Transactional
  Faculty createFaculty(Faculty faculty);

  @Transactional
  Department createDepartment(Department department);

  @Transactional
  ClassDetails createClass(ClassConfiguration classConfigurationInfo);

  @Transactional
  Folder createFolder(Folder folder);
}
