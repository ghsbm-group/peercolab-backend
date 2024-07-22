package com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.model.ClassConfiguration;

import java.util.List;

public interface SchoolRepository {

  List<Country> findAllCountries();

  List<City> findCitiesByCountry(Long countryId);

  List<University> findUniversitiesByCity(Long cityId);

  List<Faculty> findFacultiesByUniversity(Long universityId);

  List<Department> findDepartmentsByFaculty(Long facultyId);

  List<ClassConfiguration> findClassesByDepartment(Long departmentId);

  University create(University university);

  Faculty create(Faculty faculty);

  Department create(Department department);

  ClassConfiguration create(ClassConfiguration classConfigurationInfo);

  Folder create(Folder folder);


}
