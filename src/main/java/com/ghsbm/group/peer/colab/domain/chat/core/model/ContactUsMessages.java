package com.ghsbm.group.peer.colab.domain.chat.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactUsMessages {

  private Long id;
  private String userEmail;
  private String subject;
  private String content;
}
