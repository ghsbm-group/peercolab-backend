package com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing;

import org.springframework.web.multipart.MultipartFile;

//pt disk
public interface StorageService {

  byte[] load(String filename, String key);

  void store(MultipartFile file, String key);
}
