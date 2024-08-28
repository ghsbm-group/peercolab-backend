package com.ghsbm.group.peer.colab.domain.file.persistance;

import com.ghsbm.group.peer.colab.application.config.S3Buckets;
import com.ghsbm.group.peer.colab.domain.file.core.exception.StorageException;
import com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing.StorageService;
import com.ghsbm.group.peer.colab.infrastructure.s3.S3Service;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageAdapterService implements StorageService {

  private final S3Service s3Service;

  private final S3Buckets s3Buckets;

  @Autowired
  public StorageAdapterService(S3Service s3Service, S3Buckets s3Buckets) {
    this.s3Service = s3Service;
    this.s3Buckets = s3Buckets;
  }

  @Override
  public byte[] load(String filename, String key) {
    return s3Service.getObject(s3Buckets.getStorage(), key);
  }

  @Override
  public void store(MultipartFile file, String key) {
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }
      s3Service.putObject(s3Buckets.getStorage(), key, file.getBytes());
    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }
  }
}
