package com.ghsbm.group.peer.colab.domain.chat.persistence.model;

import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.model.PostLike;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * {@link Mapper}
 *
 * <p>Abstract class containing helper methods for mapping between core and entity objects.
 */
@Mapper(componentModel = "spring")
public abstract class ChatEntitiesMapper {
  public abstract List<Message> fromMessageEntities(List<MessageEntity> messages);

  public abstract Message messageFromEntity(MessageEntity savedMessage);
}
