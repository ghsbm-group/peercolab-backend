package com.ghsbm.group.peer.colab.domain.chat.persistence.model;

import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * {@link Mapper}
 *
 * <p>Abstract class containing helper methods for mapping between core and entity objects.
 */
@Mapper(componentModel = "spring")
public abstract class ChatEntitiesMapper {
  public abstract List<Message> fromMessageEntities(List<MessageEntity> messages);

  @Mappings({
    @Mapping(target = "userId", source = "user.id"),
    @Mapping(target = "messageboardId", source = "messageboard.id")
  })
  public abstract Message messageFromEntity(MessageEntity savedMessage);
}
