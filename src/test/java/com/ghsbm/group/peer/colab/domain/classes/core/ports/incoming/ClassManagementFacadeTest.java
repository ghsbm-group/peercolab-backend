package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassConfiguration;
import com.ghsbm.group.peer.colab.domain.classes.core.model.ClassDetails;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ClassManagementFacadeTest {

  public static final long CLASS_CONFIGURATION_ID = 1L;
  public static final String FOLDER_NAME = "FolderName";
  public static final long FOLDER_ID = 1L;
  public static final long DEPARTMENT_ID = 1L;
  public static final String CLASS_NAME = "ClassName";
  public static final int START_YEAR = 2009;
  public static final int NO_OF_SEMESTERS_PER_YEAR = 2;
  public static final int NO_OF_STUDY_YEARS = 4;
  @InjectMocks private ClassManagementFacade victim;

  @Mock private ClassRepository classRepository;

  private static ClassConfiguration buildValidClassConfiguration() {
    return ClassConfiguration.builder()
        .departmentId(DEPARTMENT_ID)
        .noOfStudyYears(NO_OF_STUDY_YEARS)
        .noOfSemestersPerYear(NO_OF_SEMESTERS_PER_YEAR)
        .name(CLASS_NAME)
        .startYear(START_YEAR)
        .build();
  }

  private static Folder buildFolderWithNullName() {
    return Folder.builder().classConfigurationId(CLASS_CONFIGURATION_ID).build();
  }

  private static Folder buildValidFolder() {
    return Folder.builder().classConfigurationId(CLASS_CONFIGURATION_ID).name(FOLDER_NAME).build();
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createdFolderShouldHaveTheIdSet() {
    // given
    Folder toBeCreated = buildValidFolder();
    when(classRepository.create(toBeCreated))
        .thenReturn(
            Folder.builder()
                .id(FOLDER_ID)
                .name(FOLDER_NAME)
                .classConfigurationId(CLASS_CONFIGURATION_ID)
                .build());

    // when
    Folder createdFolder = victim.createFolder(toBeCreated);

    // then
    assertEquals(createdFolder.getName(), toBeCreated.getName());
    assertEquals(createdFolder.getClassConfigurationId(), toBeCreated.getClassConfigurationId());
    assertEquals(createdFolder.getId(), FOLDER_ID);
  }

  @Test
  void createFolderShouldThrowExceptionForInvalidFolder() {
    assertThrows(NullPointerException.class, () -> victim.createFolder(buildFolderWithNullName()));
    assertThrows(NullPointerException.class, () -> victim.createFolder(null));
  }

  @Test
  void createClassShouldCreateClassStructureAndSetClassConfigurationId() {
    ClassConfiguration classConfiguration = buildValidClassConfiguration();

    ClassConfiguration savedClassConfiguration = buildValidClassConfiguration();
    savedClassConfiguration.setId(ClassManagementFacadeTest.CLASS_CONFIGURATION_ID);
    when(classRepository.create(classConfiguration)).thenReturn(savedClassConfiguration);
    when(classRepository.create(any(Folder.class))).thenReturn(Folder.builder().build());

    ClassDetails classDetails = victim.createClass(classConfiguration);

    verify(classRepository, times(NO_OF_STUDY_YEARS + NO_OF_SEMESTERS_PER_YEAR * NO_OF_STUDY_YEARS))
        .create(any(Folder.class));
    assertEquals(classDetails.getClassConfiguration(), savedClassConfiguration);
    assertEquals(classDetails.getClassStructure().getFolders().size(), NO_OF_STUDY_YEARS);
  }

  @Test
  void createClassShouldThrowExceptionForInvalidClassConfiguration() {
    var classConfigurationBuilder = buildValidClassConfiguration().toBuilder();

    assertThrows(
        NullPointerException.class,
        () -> victim.createClass(classConfigurationBuilder.departmentId(null).build()));
    assertThrows(
        NullPointerException.class,
        () -> victim.createClass(classConfigurationBuilder.noOfSemestersPerYear(null).build()));
    assertThrows(
        NullPointerException.class,
        () -> victim.createClass(classConfigurationBuilder.noOfStudyYears(null).build()));
    assertThrows(
        NullPointerException.class,
        () -> victim.createClass(classConfigurationBuilder.startYear(null).build()));
  }

  @Test
  void retrieveClassesByDepartmentIdShouldReturnAValidList() {
    List<ClassConfiguration> expectedReturnValue = List.of(buildValidClassConfiguration());
    when(classRepository.findClassesByDepartment(DEPARTMENT_ID)).thenReturn(expectedReturnValue);

    List<ClassConfiguration> response = victim.retrieveClassByDepartmentId(DEPARTMENT_ID);

    assertEquals(expectedReturnValue, response);
  }
}
