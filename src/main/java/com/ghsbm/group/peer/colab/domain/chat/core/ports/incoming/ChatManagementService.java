package com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.model.PostedMessage;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.UserIsNotEnrolledInClassConfigurationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface contract between the API and the core business logic.
 *
 * <p>Contains methods used for the management of messages.
 */
public interface ChatManagementService {
  /**
   * Retrieves the posted messages associated with a messageboard
   *
   * @param messageboardId the messagedboard id for which the posted message are retrieved.
   * @return a list of {@link PostedMessage} associated with the provided messageboard id.
   * @throws {@link UserIsNotEnrolledInClassConfigurationException} if the user is not enrolled in
   *     the class or it's not admin
   */
  List<PostedMessage> retrieveMessagesByMessageboardId(Long messageboardId);

  /**
   * Persists the message in a message board post by a user.
   *
   * @param message encapsulates message data.
   * @return a {@link Message} objects with the id attribute set.
   * @throws {@link UserIsNotEnrolledInClassConfigurationException} if the user is not enrolled in
   *     the class
   */
  @Transactional
  Message createMessage(Message message);
}
