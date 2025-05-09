package com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ghsbm.group.peer.colab.domain.chat.core.model.LatestPostedMessage;
import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.model.PostLike;
import com.ghsbm.group.peer.colab.domain.chat.core.model.PostedMessage;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing.ChatRepository;
import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.ClassManagementService;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.UserIsNotEnrolledInClassConfigurationException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.domain.infrastructure.SecurityTestUtils;
import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.UserManagementService;
import com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;

public class ChatManagementFacadeTest {

  public static final long CLASS_CONFIGURATION_ID = 1L;
  public static final long FOLDER_ID = 1L;
  public static final long FOLDER1_ID = 2L;
  public static final long MESSAGE_ID = 1L;
  public static final long MESSAGE1_ID = 2L;
  public static final long USER_ID = 1L;
  public static final String ENROLMENT_KEY = "EnrolmentKey";
  public static final String ADMIN = "ADMIN";
  public static final String CONTENT = "Content";
  public static final String LOGIN = "admin";
  public static final String PASSWORD = "Password";
  public static final Set<GrantedAuthority> authorities = new HashSet<>();

  @InjectMocks private ChatManagementFacade victim;

  @Mock private ClassRepository classRepository;

  @Mock private ChatRepository chatRepository;

  @Mock private ClassManagementService classManagementService;

  @Mock private UserManagementService userManagementService;

  private static Message buildValidMessage() {
    return Message.builder().content(CONTENT).messageboardId(FOLDER_ID).userId(USER_ID).build();
  }

  private static Message buildValidMessage(long folderId, long id, ZonedDateTime postDate) {
    return Message.builder()
        .id(id)
        .content(CONTENT)
        .messageboardId(folderId)
        .postDate(postDate)
        .userId(USER_ID)
        .build();
  }

  private static PostedMessage buildValidPostedMessage() {
    return PostedMessage.builder()
        .content(CONTENT)
        .login(LOGIN)
        .numberOfLikesUser(0L)
        .roleUser(ADMIN)
        .userId(USER_ID)
        .numberOfPostsUser(0L)
        .numberOfLikes(0L)
        .build();
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createMessageShouldHaveTheIdSet() {
    SecurityTestUtils.mockAdminUser();
    when(classRepository.enrol(ADMIN, ENROLMENT_KEY))
        .thenReturn(ClassConfiguration.builder().id(CLASS_CONFIGURATION_ID).build());
    when(classRepository.findFolderById(FOLDER_ID))
        .thenReturn(
            Folder.builder()
                .isMessageBoard(true)
                .classConfigurationId(CLASS_CONFIGURATION_ID)
                .id(FOLDER_ID)
                .build());
    when(classManagementService.userIsEnrolled(FOLDER_ID)).thenReturn(true);
    Message toBeCreated = buildValidMessage();
    when(chatRepository.create(toBeCreated))
        .thenReturn(
            Message.builder()
                .id(MESSAGE_ID)
                .messageboardId(FOLDER_ID)
                .content(CONTENT)
                .postDate(ZonedDateTime.now(ZoneId.of("Europe/Bucharest")))
                .build());
    Message createdMessage = victim.createMessage(toBeCreated);

    assertEquals(MESSAGE_ID, createdMessage.getId());
    assertEquals(FOLDER_ID, createdMessage.getMessageboardId());
    assertEquals(toBeCreated.getContent(), createdMessage.getContent());
  }

  @Test
  void createMessageShouldThrowUserIsNotEnrolledInClassConfigurationException() {
    var message = buildValidMessage();

    assertThrows(
        UserIsNotEnrolledInClassConfigurationException.class, () -> victim.createMessage(message));
  }

  @Test
  void createMessageShouldThrowExceptionForInvalidMessage() {
    assertThrows(NullPointerException.class, () -> victim.createMessage(null));
    assertThrows(
        NullPointerException.class,
        () -> victim.createMessage(Message.builder().content(CONTENT).build()));
    assertThrows(
        NullPointerException.class,
        () -> victim.createMessage(Message.builder().messageboardId(FOLDER_ID).build()));
  }

  @Test
  void retrieveMessagesByMessageboardIdShouldReturnAValidList() {
    SecurityTestUtils.mockAdminUser();
    when(userManagementService.getUserWithAuthoritiesByLogin(LOGIN))
        .thenReturn(
            Optional.of(
                User.builder()
                    .id(USER_ID)
                    .login(LOGIN)
                    .activated(true)
                    .authorities(Set.of(new Authority(AuthoritiesConstants.ADMIN)))
                    .build()));
    when(userManagementService.findOneById(any()))
        .thenReturn(
            Optional.of(
                User.builder()
                    .id(USER_ID)
                    .login(LOGIN)
                    .activated(true)
                    .authorities(Set.of(new Authority(AuthoritiesConstants.ADMIN)))
                    .build()));
    when(classRepository.enrol(ADMIN, ENROLMENT_KEY))
        .thenReturn(ClassConfiguration.builder().id(CLASS_CONFIGURATION_ID).build());
    when(classRepository.findFolderById(FOLDER_ID))
        .thenReturn(
            Folder.builder()
                .isMessageBoard(true)
                .classConfigurationId(CLASS_CONFIGURATION_ID)
                .id(FOLDER_ID)
                .build());
    when(classManagementService.userIsEnrolled(FOLDER_ID)).thenReturn(true);
    Message toBeCreated = buildValidMessage();
    when(chatRepository.create(toBeCreated))
        .thenReturn(
            Message.builder()
                .id(MESSAGE_ID)
                .messageboardId(FOLDER_ID)
                .content(CONTENT)
                .postDate(ZonedDateTime.now(ZoneId.of("Europe/Bucharest")))
                .build());
    List<Message> messages = List.of(buildValidMessage());
    List<PostedMessage> postedMessages = List.of(buildValidPostedMessage());
    when(chatRepository.findMessagesByMessageBoardId(FOLDER_ID)).thenReturn(messages);

    List<PostedMessage> list = victim.retrieveMessagesByMessageboardId(FOLDER_ID);

    assertEquals(postedMessages, list);
  }

  @Test
  void likeAMessageShouldHaveTheIdsSet() {
    Message createdMessage = buildValidMessage();
    when(chatRepository.create(createdMessage))
        .thenReturn(
            Message.builder()
                .id(MESSAGE_ID)
                .messageboardId(FOLDER_ID)
                .content(CONTENT)
                .postDate(ZonedDateTime.now())
                .build());
    PostLike toBeCreated = PostLike.builder().messageId(MESSAGE_ID).userId(USER_ID).build();
    when(chatRepository.likeAPost(MESSAGE_ID)).thenReturn(toBeCreated);

    PostLike createdPostLike = victim.likeAMessage(MESSAGE_ID);
    assertEquals(MESSAGE_ID, createdPostLike.getMessageId());
    assertEquals(FOLDER_ID, createdPostLike.getUserId());
  }

  @Test
  void likeAPostShouldReturnNullPointerException() {
    assertThrows(NullPointerException.class, () -> victim.likeAMessage(null));
  }

  @Test
  void retrieveLatestPostedMessageShouldReturnTheCorrectMessage() {
    Message firstMessage = buildValidMessage(MESSAGE_ID, FOLDER_ID, ZonedDateTime.now());
    Message secondMessage =
        buildValidMessage(MESSAGE1_ID, FOLDER1_ID, ZonedDateTime.now().plusHours(2));
    List<Long> messageBoardIds =
        List.of(firstMessage.getMessageboardId(), secondMessage.getMessageboardId());
    LatestPostedMessage created =
        LatestPostedMessage.builder()
            .lastMessagePostedTime(secondMessage.getPostDate())
            .username(LOGIN)
            .build();
    when(chatRepository.retrieveLatestPostedMessage(messageBoardIds)).thenReturn(created);

    LatestPostedMessage toBeCreated = victim.retrieveLatestPostedMessage(messageBoardIds);

    assertEquals(created.getLastMessagePostedTime(), toBeCreated.getLastMessagePostedTime());
    assertEquals(created.getUsername(), toBeCreated.getUsername());
  }
}
