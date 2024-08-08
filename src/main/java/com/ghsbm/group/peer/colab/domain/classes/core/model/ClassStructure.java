package com.ghsbm.group.peer.colab.domain.classes.core.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/** Contains the list of root folders of a Class. */
@Data
@Builder
public class ClassStructure {
  private List<Folder> folders;
}
