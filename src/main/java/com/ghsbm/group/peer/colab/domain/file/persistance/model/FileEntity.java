package com.ghsbm.group.peer.colab.domain.file.persistance.model;

import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "file")
public class FileEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String path;

  @Column(name = "file_date")
  private LocalDateTime fileDate;

  @Column(name = "user_id")
  private Long user;

  @Column(name = "folder_id")
  private Long folderId;
}
