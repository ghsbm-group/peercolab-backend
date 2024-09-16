package com.ghsbm.group.peer.colab.domain.classes.controller;

import com.ghsbm.group.peer.colab.domain.chat.controller.model.ChatMapper;
import com.ghsbm.group.peer.colab.domain.chat.controller.model.CreateMessageRequest;
import com.ghsbm.group.peer.colab.domain.chat.controller.model.CreateMessageResponse;
import com.ghsbm.group.peer.colab.domain.chat.core.model.LatestPostedMessage;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming.ChatManagementService;
import com.ghsbm.group.peer.colab.domain.classes.controller.model.*;
import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.ClassDTO;
import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.FolderDTO;
import com.ghsbm.group.peer.colab.domain.classes.controller.model.dto.FolderInfoDTO;
import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassDetails;
import com.ghsbm.group.peer.colab.domain.classes.core.model.FolderInformation;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.ClassManagementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class management API.
 *
 * <p>Contains endpoints related to class management. Ex: createClass
 */
@RestController
@RequestMapping("/classes")
public class ClassManagementController {

  private final ClassManagementService classManagementService;
  private final ClassMapper classMapper;
  private final ChatMapper chatMapper;
  private final ChatManagementService chatManagementService;

  public ClassManagementController(
      ClassManagementService classManagementService,
      ClassMapper classMapper,
      ChatMapper chatMapper,
      ChatManagementService chatManagementService) {
    this.classManagementService = classManagementService;
    this.classMapper = classMapper;
    this.chatMapper = chatMapper;
    this.chatManagementService = chatManagementService;
  }

  /**
   * Endpoint for creating a new Class.
   *
   * <p>Calling this api will create a new class and a folder structure based on the class
   * configuration passed as a parameter.
   *
   * @param createClassRequest {@link CreateClassRequest} encapsulates the class configuration
   *     parameters.
   * @return a {@link CreateClassResponse} containing the configuration identifier and the root
   *     folders for the created class.
   */
  @PostMapping("/create")
  public ResponseEntity<CreateClassResponse> createClass(
      @Valid @RequestBody final CreateClassRequest createClassRequest) {
    final var classInfo =
        classManagementService.createClass(classMapper.fromCreateClassRequest(createClassRequest));

    return ResponseEntity.ok(
        CreateClassResponse.builder()
            .classConfigurationId(classInfo.getClassConfiguration().getId())
            .folders(classMapper.foldersDTOFrom(classInfo.getClassStructure().getFolders()))
            .enrolmentKey(classInfo.getEnrolmentKey())
            .build());
  }

  /**
   * Endpoint for creating a new Folder.
   *
   * <p>Calling this api will create a new folder, a subfolder(depending on the existence of the
   * parentId parameter)
   *
   * @param createFolderRequest {@link FolderInfoDTO} encapsulates the folder configuration
   *     parameters.
   * @return a {@link CreateFolderResponse} containing the configuration identifiers for the created
   *     folder.
   */
  @PostMapping("/create-folder")
  public ResponseEntity<CreateFolderResponse> createFolder(
      @Valid @RequestBody final FolderInfoDTO createFolderRequest) {
    final var folder =
        classManagementService.createFolder(
            classMapper.fromCreateFolderInfoDTORequest(createFolderRequest));
    return ResponseEntity.ok(
        CreateFolderResponse.builder()
            .folderDTO(
                FolderDTO.builder()
                    .id(folder.getId())
                    .name(folder.getName())
                    .isMessageBoard(folder.getIsMessageBoard())
                    .build())
            .build());
  }

  /**
   * Endpoint for creating a new message board and the first posted message.
   *
   * <p>Calling this api will create a new message board (the parameter isMessageBoard is set to
   * true)
   *
   * @param createMessageBoardRequest {@link CreateMessageBoardRequest} encapsulates message board
   *     configuration parameters.
   * @return a {@link CreateMessageResponse} containing the configuration identifiers for the
   *     created messageboard and a {@link CreateMessageResponse} containing the first posted
   *     message.
   */
  @PostMapping("/create-messageboard")
  public ResponseEntity<CreateMessageBoardResponse> createMessageboard(
      @Valid @RequestBody final CreateMessageBoardRequest createMessageBoardRequest) {
    final var folder =
        classManagementService.createMessageBoard(
            classMapper.fromCreateFolderInfoDTORequest(
                createMessageBoardRequest.getFolderInfoDTO()));
    CreateMessageRequest createMessageRequest =
        CreateMessageRequest.builder()
            .content(createMessageBoardRequest.getFirstMessage())
            .messageboardId(folder.getId())
            .build();
    final var message =
        chatManagementService.createMessage(
            chatMapper.fromCreateMessageRequest(createMessageRequest));
    return ResponseEntity.ok(
        CreateMessageBoardResponse.builder()
            .folderDTO(
                FolderDTO.builder()
                    .id(folder.getId())
                    .name(folder.getName())
                    .isMessageBoard(folder.getIsMessageBoard())
                    .build())
            .createMessageResponse(
                CreateMessageResponse.builder()
                    .userId(message.getUserId())
                    .postDate(message.getPostDate())
                    .content(message.getContent())
                    .build())
            .build());
  }

  /**
   * Returns information about classes that are part of a specific department.
   *
   * @param departmentId The department identifier for which the list of classes will be returned.
   * @return A list of {@link ClassDTO} encapsulating data about classes.
   */
  @GetMapping("/byDepartment")
  public ResponseEntity<List<ClassDTO>> retrieveClassesByDepartmentId(final Long departmentId) {
    Objects.requireNonNull(departmentId);

    return ResponseEntity.ok(
        classMapper.classesDTOFrom(
            classManagementService.retrieveClassByDepartmentId(departmentId)));
  }

  /**
   * Returns information about folders that are part of a certain class and that are of type root,
   * i.e. parentId is set to null
   *
   * @param classConfigurationId The class identifier for which the list of folders will be
   *     returned.
   * @return A list of {@link FolderDTO} encapsulating data about folders.
   */
  @GetMapping("/root-folders")
  public ResponseEntity<List<FolderDTO>> retrieveRootFoldersByClassConfigurationId(
      final Long classConfigurationId) {
    Objects.requireNonNull(classConfigurationId);
    return ResponseEntity.ok(
        classMapper.foldersDTOFrom(
            classManagementService.retrieveRootFolderByClassConfigurationId(classConfigurationId)));
  }

  /**
   * Returns information about folders that are part of a certain folder and that are of type
   * subfolder, i.e. parentId is set
   *
   * @param parentId The folder identifier for which the list of folders will be returned.
   * @return A list of {@link FolderDTO} encapsulating data about folders.
   */
  @GetMapping("/subfolders")
  public ResponseEntity<List<FolderDTO>> retrieveFoldersByParentId(final Long parentId) {
    Objects.requireNonNull(parentId);
    return ResponseEntity.ok(
        classMapper.foldersDTOFrom(classManagementService.retrieveFolderByParentId(parentId)));
  }

  /**
   * Returns information about folders and the class through which to navigate to reach a specific
   * folder, used to create a path
   *
   * @param folderId The folder identifier for which the list of folders and class configuration
   *     will be returned.
   * @return A {@link PathResponse} encapsulating a list of {@link FolderDTO} and a {@link ClassDTO}
   *     object.
   */
  @GetMapping("/path")
  public ResponseEntity<PathResponse> retrievePath(final Long folderId) {
    Objects.requireNonNull(folderId);
    return ResponseEntity.ok(
        PathResponse.builder()
            .path(classMapper.foldersDTOFrom(classManagementService.getFolderPath(folderId)))
            .classDTO(
                classMapper.classDTOFromClassConfiguration(
                    classManagementService.retrieveClassConfigurationByFolderId(folderId)))
            .build());
  }

  /**
   * Endpoint for renaming a folder.
   *
   * <p>Calling this api will rename folder or a subfolder
   *
   * @param renameFolderRequest {@link RenameFolderRequest} encapsulates the parameters needed to
   *     update the folder name
   * @return a {@link RenameFolderResponse} containing the configuration identifiers for the updated
   *     folder.
   */
  @PostMapping("/rename-folder")
  public ResponseEntity<RenameFolderResponse> renameFolder(
      @Valid @RequestBody final RenameFolderRequest renameFolderRequest) {

    final var folder =
        classManagementService.renameFolder(
            classMapper.fromRenameFolderRequest(renameFolderRequest));
    return ResponseEntity.ok(
        RenameFolderResponse.builder()
            .folderDTO(
                FolderDTO.builder()
                    .id(folder.getId())
                    .name(folder.getName())
                    .isMessageBoard(folder.getIsMessageBoard())
                    .build())
            .build());
  }

  /**
   * Enrols the current logged-in user in the class associated with the enrolmentKey.
   *
   * @param enrolmentKey the key used to enrol the user in a class.
   * @return a {@link EnrolmentResponse} which contains data about the class in which the user was
   *     enroled.
   */
  @PostMapping("/enrol")
  public ResponseEntity<EnrolmentResponse> enrolByActivationKey(
      @RequestBody @NotNull String enrolmentKey) {
    ClassDetails classInfo = classManagementService.enrolStudent(enrolmentKey);
    return ResponseEntity.ok(
        EnrolmentResponse.builder()
            .classConfigurationId(classInfo.getClassConfiguration().getId())
            .folders(classMapper.foldersDTOFrom(classInfo.getClassStructure().getFolders()))
            .build());
  }

  /**
   * Returns the enrolment key that is part of a specific class configuration.
   *
   * @param classConfigurationId The class configuration identifier
   * @return the enrolment key associated with a specific class configuration.
   */
  @GetMapping("/enrolment-key")
  public ResponseEntity<String> getEnrolmentKeyBasedOnClassConfigurationId(
      @NotNull final Long classConfigurationId) {

    return ResponseEntity.ok(
        classManagementService.getEnrolmentKeyByClassConfigurationId(classConfigurationId));
  }

  /**
   * Returns the folder information like the number of topics, number of posts, information about
   * the latest posted message
   *
   * @param folderId The folder identifier
   * @return A {@link FolderInformationResponse} encapsulating data about the specific folder.
   */
  @GetMapping("/folder-information")
  public ResponseEntity<FolderInformationResponse> retrieveFolderInformation(
      @NotNull final Long folderId) {
    LatestPostedMessage latestPostedMessage =
        chatManagementService.retrieveLatestPostedMessage(
            classManagementService.getMessageBoardsIds(folderId));

    Long numberOfUnreadMessages =
        classManagementService.findUserMessageBoardAccess(folderId) != null
            ? chatManagementService.countMessagesAfterDate(
                classManagementService.findUserMessageBoardAccess(folderId).getLastAccessDate())
            : classManagementService.countAllMessagesByMessageBoardId(folderId);

    FolderInformation folderInformation =
        classManagementService.retrieveFolderInformation(folderId);
    if (!Objects.isNull(latestPostedMessage)) {
      folderInformation.setMessageBoard(latestPostedMessage.getMessageBoard());
      folderInformation.setLastMessagePostedTime(latestPostedMessage.getLastMessagePostedTime());
      folderInformation.setUsername(latestPostedMessage.getUsername());
    }
    folderInformation.setNumberOfUnreadMessages(numberOfUnreadMessages);
    return ResponseEntity.ok(
        classMapper.folderInformationResponseFromFolderInformation(folderInformation));
  }

  /**
   * Returns information about classes that the logged user is enrolled.
   *
   * @return a list of {@link EnrolledClassesResponse} that encapsulates information about the
   *     classes that logged user is enrolled.
   */
  @GetMapping("/enrolled-classes")
  public ResponseEntity<List<EnrolledClassesResponse>> getEnrolledClassesOfCurrentUser() {
    return ResponseEntity.ok(
        classMapper.enrolledClassesResponseFromClassConfiguration(
            classManagementService.getEnrolledClassOfCurrentUser()));
  }

  /**
   * Deletes a folder or a message board if it's empty.
   *
   * @param folderId The folder identifier returns A 200 ok response if successful.
   */
  @DeleteMapping("/folder")
  @ResponseStatus(HttpStatus.OK)
  public void deleteFolder(@NotNull final Long folderId) {
    classManagementService.deleteFolder(folderId);
  }
}
