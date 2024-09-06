package com.ghsbm.group.peer.colab.domain.classes.persistence.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMessageBoardAccessId implements Serializable {

    private Long userId;
    private Long messageboardId;

}
