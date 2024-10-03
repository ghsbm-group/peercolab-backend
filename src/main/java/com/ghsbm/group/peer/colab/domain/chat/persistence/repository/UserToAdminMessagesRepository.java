package com.ghsbm.group.peer.colab.domain.chat.persistence.repository;

import com.ghsbm.group.peer.colab.domain.chat.persistence.model.UserToAdminMessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserToAdminMessagesRepository
    extends JpaRepository<UserToAdminMessagesEntity, Long> {}
