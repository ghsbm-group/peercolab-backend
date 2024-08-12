package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming.ChatManagementFacade;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing.ChatRepository;
import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.domain.infrastructure.SecurityTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ChatManagementFacadeTest {

  public static final long CLASS_CONFIGURATION_ID = 1L;
  public static final long FOLDER_ID = 1L;
  public static final long MESSAGE_ID = 1L;
  public static final String ENROLMENT_KEY = "EnrolmentKey";
  public static final String ADMIN = "admin";
  public static final String CONTENT = "Content";

  @InjectMocks private ChatManagementFacade victim;

  @Mock private ClassRepository classRepository;

  @Mock private ChatRepository chatRepository;

  private Message buildValidMessage() {
    return Message.builder().content(CONTENT).messageboardId(FOLDER_ID).build();
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
    when(classRepository.isEnrolled(ADMIN, CLASS_CONFIGURATION_ID)).thenReturn(true);
    Message toBeCreated = buildValidMessage();
    when(chatRepository.create(toBeCreated))
        .thenReturn(
            Message.builder()
                .id(MESSAGE_ID)
                .messageboardId(FOLDER_ID)
                .content(CONTENT)
                .postDate(LocalDateTime.now())
                .build());
    Message createdMessage = victim.createMessage(toBeCreated);

    assertEquals(MESSAGE_ID, createdMessage.getId());
    assertEquals(FOLDER_ID, createdMessage.getMessageboardId());
    assertEquals(toBeCreated.getContent(), createdMessage.getContent());
  }
}
