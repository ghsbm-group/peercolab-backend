package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import com.ghsbm.group.peer.colab.domain.chat.core.model.UserToAdminMessages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserActionMapper {

  UserToAdminMessages fromUserToAdminMessageRequest(
      UserToAdminMessagesRequest userToAdminMessagesRequest);

  @Mapping(source = "userEmail", target = "email")
  UserToAdminMessagesResponse fromUserToAdminMessages(UserToAdminMessages userToAdminMessages);

  List<UserToAdminMessagesResponse> fromUserToAdminMessagesList(
      List<UserToAdminMessages> userToAdminMessages);
}
