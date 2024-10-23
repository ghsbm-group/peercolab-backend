package com.ghsbm.group.peer.colab.domain.chat.persistence.model;

import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.model.ContactUsMessages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * {@link Mapper}
 *
 * <p>Abstract class containing helper methods for mapping between core and entity objects.
 */
@Mapper(componentModel = "spring")
public abstract class ChatEntitiesMapper {
  public abstract List<Message> fromMessageEntities(List<MessageEntity> messages);

  @Mapping(source = "edited", target = "isEdited")
  public abstract Message messageFromEntity(MessageEntity savedMessage);

  public abstract ContactUsMessages fromEntity(
      ContactUsMessagesEntity userToAdminMessagesEntity);

  public abstract List<ContactUsMessages> fromEntities(
      List<ContactUsMessagesEntity> userToAdminMessagesEntities);
}
