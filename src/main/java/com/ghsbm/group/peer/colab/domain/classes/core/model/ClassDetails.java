package com.ghsbm.group.peer.colab.domain.classes.core.model;

import lombok.Data;
import lombok.Getter;

/** Encapsulates the class configuration and class structure. */
@Data
@Getter
public class ClassDetails {
  private ClassConfiguration classConfiguration;
  private ClassStructure classStructure;
  private String enrolmentKey;
}
