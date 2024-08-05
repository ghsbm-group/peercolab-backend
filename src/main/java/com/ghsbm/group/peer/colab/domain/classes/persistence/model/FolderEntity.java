package com.ghsbm.group.peer.colab.domain.classes.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entity class for the folder table. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "folder")
public class FolderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  @Column(name = "is_messageboard")
  private Boolean isMessageBoard;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private FolderEntity parent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_configuration_id")
  private ClassConfigurationEntity classConfiguration;
}
