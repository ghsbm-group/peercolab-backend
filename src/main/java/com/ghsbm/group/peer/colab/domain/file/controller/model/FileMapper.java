package com.ghsbm.group.peer.colab.domain.file.controller.model;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} Contains methods for transforming core domain objects into dto's and back.
 *
 * <p>Mapstruct generates an implementation based on this interface contract.
 */
@Mapper(componentModel = "spring")
public interface FileMapper {
    File fromUploadFileRequest(UploadFileRequest uploadFileRequest);
}
