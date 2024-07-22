package com.ghsbm.group.peer.colab.domain.classes.core.model;

import java.util.List;
import lombok.Data;

/** Contains the list of root folders of a Class. */
@Data
public class ClassStructure {
  private List<Folder> folders;
}
