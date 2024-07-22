package com.ghsbm.group.peer.colab.domain.classes.core.model;

import lombok.Data;

/** Encapsulates the class configuration and class structure. */
@Data
public class ClassDetails {
  private ClassConfiguration classConfiguration;
  private ClassStructure classStructure;
}
