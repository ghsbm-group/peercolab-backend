package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolManagementFacade implements SchoolManagementService {

  @Autowired private SchoolRepository universityRepository;

  @Override
  public List<Country> retrieveAllCountries() {
    return universityRepository.findAllCountries();
  }

  @Override
  public List<City> retrieveCityByCountryId(Long countryId) {
    return universityRepository.findCitiesByCountry(countryId);
  }

  @Override
  public List<University> retrieveUniversityByCityId(Long cityId) {
    return universityRepository.findUniversitiesByCity(cityId);
  }

  @Override
  public List<Faculty> retrieveFacultyByUniversityId(Long universityId) {
    return universityRepository.findFacultiesByUniversity(universityId);
  }

  @Override
  public List<Department> retrieveDepartmentByFacultyId(Long facultyId) {
    return universityRepository.findDepartmentsByFaculty(facultyId);
  }

  @Override
  public University createUniversity(University university) {
    return universityRepository.create(university);
  }

  @Override
  public Faculty createFaculty(Faculty faculty) {
    return universityRepository.create(faculty);
  }

  @Override
  public Department createDepartment(Department department) {
    return universityRepository.create(department);
  }
}
