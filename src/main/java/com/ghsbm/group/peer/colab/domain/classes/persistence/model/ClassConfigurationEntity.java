package com.ghsbm.group.peer.colab.domain.classes.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class for the class_configuration table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "class_configuration")
public class ClassConfigurationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "start_year")
  private Integer startYear;

  @Column(name = "no_of_study_years")
  private Integer noOfStudyYears;

  @Column(name = "no_of_semesters_per_year")
  private Integer noOfSemestersPerYear;

  @Column(name = "department_id")
  private Long departmentId;
}
