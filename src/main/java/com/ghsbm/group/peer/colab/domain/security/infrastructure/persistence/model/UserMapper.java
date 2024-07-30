package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model;

import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User fromEntity(UserEntity userEntity);

  UserEntity fromDomain(User u);

  Authority fromEntity(AuthorityEntity authorityEntity);
}
