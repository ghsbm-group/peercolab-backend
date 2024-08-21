package com.ghsbm.group.peer.colab.domain.chat.controller;

import com.ghsbm.group.peer.colab.domain.chat.controller.model.*;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming.ChatManagementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Chat management API.
 *
 * <p>Contains endpoints related to messages management. Ex: createMessage
 */
@RestController
@RequestMapping("/chat")
public class ChatManagementController {

  private final PostedMessageDtoMapper postedMessageDtoMapper;

  private final ChatManagementService chatManagementService;

  private final ChatMapper chatMapper;

  public ChatManagementController(
      PostedMessageDtoMapper postedMessageDtoMapper,
      ChatManagementService chatManagementService,
      ChatMapper chatMapper) {
    this.postedMessageDtoMapper = postedMessageDtoMapper;
    this.chatManagementService = chatManagementService;
    this.chatMapper = chatMapper;
  }

  /**
   * Endpoint for creating a new Message post by a user.
   *
   * <p>Calling this api will create a new message structure based on message passed as a parameter
   * send by a user.
   *
   * @param createMessageRequest {@link CreateMessageRequest} encapsulates the message parameters.
   * @return a {@link CreateMessageResponse} containing the configuration identifiers for the
   *     created message
   */
  @PostMapping("/create-message")
  public ResponseEntity<CreateMessageResponse> createMessage(
      @Valid @RequestBody final CreateMessageRequest createMessageRequest) {

    final var message =
        chatManagementService.createMessage(
            chatMapper.fromCreateMessageRequest(createMessageRequest));
    return ResponseEntity.ok(
        CreateMessageResponse.builder()
            .content(message.getContent())
            .userId(message.getUserId())
            .postDate(message.getPostDate())
            .build());
  }

  /**
   * Endpoint for liking a message by a user
   *
   * @param messageId The identifier of the appreciated message
   * @return a {@link LikeAPostResponse} contains information about the appreciated message and the
   *     user who appreciated the message
   */
  @PostMapping("/like-a-post")
  public ResponseEntity<LikeAPostResponse> likeAPost(@NotNull final Long messageId) {
    final var postlike = chatManagementService.likeAMessage(messageId);
    return ResponseEntity.ok(
        LikeAPostResponse.builder()
            .messageId(postlike.getMessageId())
            .userId(postlike.getUserId())
            .build());
  }

  /**
   * Returns information about messages that are part of a specific messageboard.
   *
   * @param messageboardId The messageboard identifier for which the list of messages will be
   *     returned.
   * @return A list of {@link PostedMessageDTO} encapsulating data about posted messages.
   */
  @GetMapping("/messages")
  public ResponseEntity<List<PostedMessageDTO>> retrieveMessagesByMessageboardId(
      final Long messageboardId) {
    Objects.requireNonNull(messageboardId);

    return ResponseEntity.ok(
        postedMessageDtoMapper.postedMessagesDTOFrom(
            chatManagementService.retrieveMessagesByMessageboardId(messageboardId)));
  }
}
