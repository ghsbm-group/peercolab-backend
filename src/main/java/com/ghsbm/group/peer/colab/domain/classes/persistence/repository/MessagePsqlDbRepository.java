package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/** JPA repository for {@link MessageEntity} */
public interface MessagePsqlDbRepository extends JpaRepository<MessageEntity, Long> {}
