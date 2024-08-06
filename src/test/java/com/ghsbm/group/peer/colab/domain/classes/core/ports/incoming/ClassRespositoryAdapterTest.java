package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import com.ghsbm.group.peer.colab.domain.classes.persistence.ClassRepositoryAdapter;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.ClassEntitiesMapper;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.FolderEntity;
import com.ghsbm.group.peer.colab.domain.classes.persistence.repository.FolderPsqlDbRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ClassRespositoryAdapterTest {

  public static final String FOLDER_ENTITY_NAME = "FolderEntityName";
  public static final long FOLDER_ENTITY_ID = 1L;

  @InjectMocks private ClassRepositoryAdapter victim;

  @Mock private FolderPsqlDbRespository folderPsqlDbRespository;

  @Mock private ClassEntitiesMapper classEntitiesMapper;

  FolderEntity buildValidFolderEntity() {
    return FolderEntity.builder().id(FOLDER_ENTITY_ID).name(FOLDER_ENTITY_NAME).build();
  }

  Folder buildValidFolder() {
    return Folder.builder().id(FOLDER_ENTITY_ID).name(FOLDER_ENTITY_NAME).build();
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void renameFolder() {
    FolderEntity folderEntity = buildValidFolderEntity();
    Folder folderWithNewName = buildValidFolder();
    Folder expectedValue = buildValidFolder();
    when(folderPsqlDbRespository.getReferenceById(FOLDER_ENTITY_ID)).thenReturn(folderEntity);
    when(classEntitiesMapper.folderFromEntity(folderEntity)).thenReturn(expectedValue);

    Folder response = victim.renameFolder(folderWithNewName);

    assertEquals(expectedValue, response);
  }
}
