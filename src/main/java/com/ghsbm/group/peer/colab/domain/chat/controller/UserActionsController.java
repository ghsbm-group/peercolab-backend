package com.ghsbm.group.peer.colab.domain.chat.controller;

import com.ghsbm.group.peer.colab.domain.chat.controller.model.*;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming.ChatManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-actions")
public class UserActionsController {

  private final ChatManagementService chatManagementService;

  private final UserActionMapper userActionMapper;

  public UserActionsController(
      ChatManagementService chatManagementService, UserActionMapper userActionMapper) {
    this.chatManagementService = chatManagementService;
    this.userActionMapper = userActionMapper;
  }

  @PostMapping("/contact-us")
  public ResponseEntity<ContactUsMessagesResponse> sendMessageToAdmin(
      @Valid @RequestBody final ContactUsMessagesRequest sendMessageToAdmin) {

    final var messageToAdmin =
        chatManagementService.sendMessageToAdmin(
            userActionMapper.fromUserToAdminMessageRequest(sendMessageToAdmin));
    return ResponseEntity.ok(
        ContactUsMessagesResponse.builder()
            .id(messageToAdmin.getId())
            .email(messageToAdmin.getUserEmail())
            .subject(messageToAdmin.getSubject())
            .content(messageToAdmin.getContent())
            .build());
  }

  @GetMapping("/messages")
  public ResponseEntity<List<ContactUsMessagesResponse>> getMessages() {
    return ResponseEntity.ok(
        userActionMapper.fromUserToAdminMessagesList(
            chatManagementService.retrieveMessagesFromUsers()));
  }
}
