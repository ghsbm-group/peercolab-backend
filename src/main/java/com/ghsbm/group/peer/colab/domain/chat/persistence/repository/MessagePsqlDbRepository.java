package com.ghsbm.group.peer.colab.domain.chat.persistence.repository;

import com.ghsbm.group.peer.colab.domain.chat.persistence.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** JPA repository for {@link MessageEntity} */
public interface MessagePsqlDbRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByMessageboardId(Long messageboardId);
}
