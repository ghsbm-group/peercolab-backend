package com.ghsbm.group.peer.colab.domain.chat.persistence.repository;

import com.ghsbm.group.peer.colab.domain.chat.persistence.model.PostLikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesPsqlDbRepository extends JpaRepository<PostLikesEntity, Long> {

    Long countByMessageId(Long messageId);
}
