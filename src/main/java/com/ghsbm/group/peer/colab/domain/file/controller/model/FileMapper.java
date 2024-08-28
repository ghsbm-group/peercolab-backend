package com.ghsbm.group.peer.colab.domain.file.controller.model;

import com.ghsbm.group.peer.colab.domain.file.core.model.FileInfo;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} Contains methods for transforming core domain objects into dto's and back.
 *
 * <p>Mapstruct generates an implementation based on this interface contract.
 */
@Mapper(componentModel = "spring")
public interface FileMapper {

  List<FileDTO> map(List<FileInfo> fileInfoInfos);
}
