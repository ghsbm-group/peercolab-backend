package com.ghsbm.group.peer.colab.domain.classes.persistence.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrolmentId implements Serializable {
  private Long userId;
  private Long classConfigurationId;
}
