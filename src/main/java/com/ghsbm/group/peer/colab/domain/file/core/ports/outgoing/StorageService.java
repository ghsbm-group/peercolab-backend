package com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

//pt disk
public interface StorageService {

    Path load(String filename);

    void store(MultipartFile file);

}
