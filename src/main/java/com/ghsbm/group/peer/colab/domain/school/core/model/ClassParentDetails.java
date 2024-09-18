package com.ghsbm.group.peer.colab.domain.school.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassParentDetails {
  Faculty faculty;
  Department department;
  Country country;
  City city;
  University university;
}
