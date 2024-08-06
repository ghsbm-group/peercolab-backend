package com.ghsbm.group.peer.colab.domain.classes.persistence.model;

import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entity class for the enrolment table. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrolment")
public class EnrolmentEntity {
  @EmbeddedId private EnrolmentId id;
  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private UserEntity user;
  @ManyToOne
  @MapsId("classConfigurationId")
  @JoinColumn(name = "class_configuration_id")
  private ClassConfigurationEntity classConfiguration;
  @Column(nullable = false, name = "enrolment_date")
  private LocalDateTime enrolmentDate;

  public EnrolmentEntity(UserEntity user, ClassConfigurationEntity classConfigurationEntity) {
    this.id = new EnrolmentId(user.getId(), classConfigurationEntity.getId());
    this.user = user;
    this.classConfiguration = classConfigurationEntity;
    this.enrolmentDate = LocalDateTime.now();
  }
}
