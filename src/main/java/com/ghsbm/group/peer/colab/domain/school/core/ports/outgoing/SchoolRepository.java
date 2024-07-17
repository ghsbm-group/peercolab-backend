package com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.model.Class;

import java.util.List;

public interface SchoolRepository {

  List<Country> findAllCountries();

  List<City> findCitiesByCountry(Long countryId);

  List<University> findUniversitiesByCity(Long cityId);

  List<Faculty> findFacultiesByUniversity(Long universityId);

  List<Department> findDepartmentsByFaculty(Long facultyId);

  University create(University university);

  Faculty create(Faculty faculty);

  Department create(Department department);

  Class create(Class classInfo);
}
