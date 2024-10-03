package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactUsMessagesRequest {

  @NotNull @Email private String userEmail;

  @NotNull
  @Size(min = 1, max = 200)
  private String subject;

  @NotNull
  @Size(min = 1, max = 10000)
  private String content;
}
