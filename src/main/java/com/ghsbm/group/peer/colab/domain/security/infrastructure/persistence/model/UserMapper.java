package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model;

import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.RequestAuthority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User fromEntity(UserEntity userEntity);

  UserEntity fromDomain(User u);

  Authority fromEntity(AuthorityEntity authorityEntity);

  @Mappings({
    @Mapping(target = "userId", source = "id.userId"),
    @Mapping(target = "authorityName", source = "id.authorityName")
  })
  RequestAuthority fromEntity(RequestAuthorityEntity requestAuthorityEntity);

  List<RequestAuthority> fromEntities(List<RequestAuthorityEntity> requestAuthorityEntity);
}
