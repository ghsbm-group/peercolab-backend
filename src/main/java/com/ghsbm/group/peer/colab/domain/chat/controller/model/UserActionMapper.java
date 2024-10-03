package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import com.ghsbm.group.peer.colab.domain.chat.core.model.ContactUsMessages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserActionMapper {

  ContactUsMessages fromUserToAdminMessageRequest(
      ContactUsMessagesRequest contactUsMessagesRequest);

  @Mapping(source = "userEmail", target = "email")
  ContactUsMessagesResponse fromUserToAdminMessages(ContactUsMessages contactUsMessages);

  List<ContactUsMessagesResponse> fromUserToAdminMessagesList(
      List<ContactUsMessages> contactUsMessages);
}
