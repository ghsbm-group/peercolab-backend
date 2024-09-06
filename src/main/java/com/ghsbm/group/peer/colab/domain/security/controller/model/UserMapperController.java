package com.ghsbm.group.peer.colab.domain.security.controller.model;

import com.ghsbm.group.peer.colab.domain.security.core.model.UserAuthorityRequest;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} Contains methods for transforming core domain objects into dto's and back.
 *
 * <p>Mapstruct generates an implementation based on this interface contract.
 */
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapperController {

  List<UserAuthorityRequestResponse> userAuthorityRequestResponsesFromUserAuthorityRequest(
      List<UserAuthorityRequest> userAuthorityRequests);
}
