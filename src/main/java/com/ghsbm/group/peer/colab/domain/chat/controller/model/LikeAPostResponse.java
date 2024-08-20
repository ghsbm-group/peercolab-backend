package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeAPostResponse {
    private Long userId;
    private Long messageId;
}
