package com.ghsbm.group.peer.colab.application.config;

import com.ghsbm.group.peer.colab.infrastructure.s3.FakeS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
  @Value("${aws.region}")
  private String awsRegion;

  @Value("${aws.s3.mock}")
  private boolean mock;

  @Bean
  public S3Client s3Client() {
    if (mock) {
      return new FakeS3();
    }
    return S3Client.builder().region(Region.of(awsRegion)).build();
  }
}
