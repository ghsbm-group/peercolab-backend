package com.ghsbm.group.peer.colab.domain.classes.controller.model.dto;

import com.ghsbm.group.peer.colab.domain.school.controller.model.CityDTO;
import com.ghsbm.group.peer.colab.domain.school.controller.model.CountryDTO;
import com.ghsbm.group.peer.colab.domain.school.controller.model.DepartmentDTO;
import com.ghsbm.group.peer.colab.domain.school.controller.model.FacultyDTO;
import com.ghsbm.group.peer.colab.domain.school.controller.model.UniversityDTO;
import lombok.Data;

/** Encapsulates class parent data. */
@Data
public class ClassParentDetailsDTO {
  CityDTO city;
  CountryDTO country;
  FacultyDTO faculty;
  UniversityDTO university;
  DepartmentDTO department;
}
