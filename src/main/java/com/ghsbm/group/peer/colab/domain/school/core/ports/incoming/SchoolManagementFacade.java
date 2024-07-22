package com.ghsbm.group.peer.colab.domain.school.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.school.core.model.*;
import com.ghsbm.group.peer.colab.domain.school.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.school.core.ports.outgoing.SchoolRepository;

import java.util.ArrayList;
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
  public List<ClassConfiguration> retrieveClassByDepartmentId(Long classId) {
    return universityRepository.findClassesByDepartment(classId);
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

  @Override
  public ClassDetails createClass(ClassConfiguration classConfigurationInfo) {
    ClassConfiguration classConfiguration = universityRepository.create(classConfigurationInfo);

    // method returns ClassDetails type that contains ClassStructure and ClassDetails
    ClassDetails classDetails = new ClassDetails();
    ClassStructure classStructure = new ClassStructure();
    List<Folder> folders = new ArrayList<>();

    for (int i = 1;
        i <= classConfigurationInfo.getNoOfStudyYears();
        i++) { // for creating years folders
      Folder yearFolder =
          Folder.builder()
              .name("Year " + i)
              .classConfigurationId(classConfiguration.getId())
              .build();

      Folder savedYearFolder = universityRepository.create(yearFolder);
      folders.add(savedYearFolder); // add in ClassStructure

      for (int j = 1;
          j <= classConfigurationInfo.getNoOfSemestersPerYear();
          j++) { // for creating semesters folders
        Folder semesterFolder =
            Folder.builder()
                .name("Semester " + j)
                .parentId(savedYearFolder.getId()) // previously saved year id
                .classConfigurationId(classConfiguration.getId())
                .build();
        Folder savedSemesterFolder = universityRepository.create(semesterFolder);
        folders.add(savedSemesterFolder); // add in ClassStructure
      }
    }
    classStructure.setFolders(folders);
    classDetails.setClassStructure(classStructure);
    classDetails.setClassConfiguration(classConfiguration);

    return classDetails;
  }

  @Override
  public Folder createFolder(Folder folder) {
    return universityRepository.create(folder);
  }
}
