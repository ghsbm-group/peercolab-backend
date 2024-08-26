package com.ghsbm.group.peer.colab.domain.classes.core.model;

import lombok.Builder;
import lombok.Data;

/** Encapsulates enrolment data. */
@Data
@Builder
public class Enrolment {

    private Long userId;
    private Long classConfigurationId;
}
