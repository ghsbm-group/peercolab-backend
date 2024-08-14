package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import com.ghsbm.group.peer.colab.domain.classes.controller.model.CreateFolderRequest;
import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} Contains methods for transforming core domain objects into dto's and back.
 *
 * <p>Mapstruct generates an implementation based on this interface contract.
 */
@Mapper(componentModel = "spring")
public interface ChatMapper {

  /**
   * Builds a {@link Message} core domain based on a {@link CreateFolderRequest} instance.
   *
   * @param createMessageRequest encapsulates the requests paramaeters.
   * @return a {@link Message} object build based on the request parameter.
   */
  Message fromCreateMessageRequest(CreateMessageRequest createMessageRequest);
}
