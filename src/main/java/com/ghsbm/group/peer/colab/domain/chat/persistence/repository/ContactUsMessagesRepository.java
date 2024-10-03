package com.ghsbm.group.peer.colab.domain.chat.persistence.repository;

import com.ghsbm.group.peer.colab.domain.chat.persistence.model.ContactUsMessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsMessagesRepository
    extends JpaRepository<ContactUsMessagesEntity, Long> {}
