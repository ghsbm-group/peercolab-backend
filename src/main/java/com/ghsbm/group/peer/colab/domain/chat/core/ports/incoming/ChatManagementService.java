package com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.chat.core.model.*;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.UserIsNotEnrolledInClassConfigurationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
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

  /**
   * Retrieves the latest posted message from a message board.
   *
   * @param folderId The message board id from where the last posted message is extracted.
   * @return A {@link LatestPostedMessage} object.
   */
  @Transactional
  LatestPostedMessage retrieveLatestPostedMessage(List<Long> folderId);

  /**
   * Persists the appreciation of a message made by a user
   *
   * @param messageId The id of the message being rated
   * @return a {@link PostLike} object.
   */
  @Transactional
  PostLike likeAMessage(Long messageId);

  /**
   * Returns the number of posted messages after a specific date
   *
   * @param lastAccessDate the specific date for counting the posted messages
   * @return the number of posted messages after a specific date
   */
  @Transactional
  Long countMessagesAfterDate(ZonedDateTime lastAccessDate);

  @Transactional
  ContactUsMessages sendMessageToAdmin(ContactUsMessages contactUsMessages);

  @Transactional
  List<ContactUsMessages> retrieveMessagesFromUsers();
}
