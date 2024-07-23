package com.ghsbm.group.peer.colab.domain.classes.controller;

import com.ghsbm.group.peer.colab.domain.classes.controller.model.*;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.ClassManagementService;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  public ClassManagementController(
      ClassManagementService classManagementService, ClassMapper classMapper) {
    this.classManagementService = classManagementService;
    this.classMapper = classMapper;
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
      @RequestBody final CreateClassRequest createClassRequest) {
    Objects.requireNonNull(createClassRequest);
    Objects.requireNonNull(createClassRequest.getDepartmentId());
    Objects.requireNonNull(createClassRequest.getName());
    Objects.requireNonNull(createClassRequest.getStartYear());
    Objects.requireNonNull(createClassRequest.getNoOfStudyYears());
    Objects.requireNonNull(createClassRequest.getNoOfSemestersPerYear());

    final var classInfo =
        classManagementService.createClass(classMapper.fromCreateClassRequest(createClassRequest));

    return ResponseEntity.ok(
        CreateClassResponse.builder()
            .classConfigurationId(classInfo.getClassConfiguration().getId())
            .folders(classMapper.foldersDTOFrom(classInfo.getClassStructure().getFolders()))
            .build());
  }

  /**
   * Endpoint for creating a new Folder.
   *
   * <p>Calling this api will create a new folder or a subfolder depending on the existence of the
   * parentId parameter
   *
   * @param createFolderRequest {@link CreateFolderRequest} encapsulates the folder configuration
   *     parameters.
   * @return a {@link CreateFolderResponse} containing the configuration identifiers for the created
   *     folder.
   */
  @PostMapping("/create-folder")
  public ResponseEntity<CreateFolderResponse> createFolder(
      @RequestBody final CreateFolderRequest createFolderRequest) {
    Objects.requireNonNull(createFolderRequest);
    Objects.requireNonNull(createFolderRequest.getName());
    Objects.requireNonNull(createFolderRequest.getClassConfigurationId());
    final var folder =
        classManagementService.createFolder(
            classMapper.fromCreateFolderRequest(createFolderRequest));
    return ResponseEntity.ok(
        CreateFolderResponse.builder().id(folder.getId()).name(folder.getName()).build());
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
}
