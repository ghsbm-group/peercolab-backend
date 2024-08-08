package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.MessageDTO;
import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.PostedMessageDTO;
import com.ghsbm.group.peer.colab.domain.classes.core.model.PostedMessage;
import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.UserDTO;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Mapper for the entity {@link PostedMessage} and its DTO called {@link PostedMessageDTO}.
 *
 * <p>Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct support is
 * still in beta, and requires a manual step with an IDE.
 */
@Service
public class PostedMessageDtoMapper {

  public List<PostedMessageDTO> postedMessagesDTOFrom(List<PostedMessage> postedMessages) {
    if (postedMessages == null) {
      return Collections.EMPTY_LIST;
    }

    List<PostedMessageDTO> list = new ArrayList<PostedMessageDTO>(postedMessages.size());
    for (PostedMessage postedMessage : postedMessages) {
      list.add(toPostedMessageDTO(postedMessage));
    }
    Collections.sort(list, Comparator.comparing(o -> o.getMessageDTO().getPostDate()));

    return list;
  }

  public PostedMessageDTO toPostedMessageDTO(PostedMessage message) {
    if (message == null) {
      return null;
    }
    PostedMessageDTO postedMessageDTO = new PostedMessageDTO();
    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setId(message.getId());
    messageDTO.setContent(message.getContent());
    messageDTO.setPostDate(message.getPostDate());
    UserDTO userDTO = new UserDTO();
    userDTO.setId(message.getUserId());
    userDTO.setLogin(message.getLogin());
    postedMessageDTO.setUserDTO(userDTO);
    postedMessageDTO.setMessageDTO(messageDTO);
    return postedMessageDTO;
  }
}
