package com.ghsbm.group.peer.colab.domain.chat.core.model;

import lombok.Builder;
import lombok.Data;

/** Encapsulates information about liking a post. */
@Data
@Builder
public class PostLike {
  private Long userId;
  private Long messageId;
}
