package com.ghsbm.group.peer.colab.domain.file.persistance.repository;

import com.ghsbm.group.peer.colab.domain.file.persistance.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilePsqlDbRepository extends JpaRepository<FileEntity, Long> {

}
