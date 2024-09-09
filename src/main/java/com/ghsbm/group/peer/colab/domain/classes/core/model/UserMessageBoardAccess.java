package com.ghsbm.group.peer.colab.domain.classes.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

/** Encapsulates information about the last time a user access a messageboard. */
@Data
@Builder
public class UserMessageBoardAccess {

    private Long userId;
    private Long messageboardId;
    private ZonedDateTime lastAccessDate;
}
