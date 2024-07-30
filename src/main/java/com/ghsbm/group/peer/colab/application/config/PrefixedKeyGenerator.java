package com.ghsbm.group.peer.colab.application.config;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;

public class PrefixedKeyGenerator implements KeyGenerator {

  private final String prefix;
  private String shortCommitId = null;
  private Instant time = null;
  private String version = null;

  /**
   * <p>Constructor for PrefixedKeyGenerator.</p>
   *
   * @param gitProperties a {@link org.springframework.boot.info.GitProperties} object.
   * @param buildProperties a {@link org.springframework.boot.info.BuildProperties} object.
   */
  public PrefixedKeyGenerator(GitProperties gitProperties, BuildProperties buildProperties) {
    prefix = generatePrefix(gitProperties, buildProperties);
  }

  String getPrefix() {
    return prefix;
  }

  private String generatePrefix(GitProperties gitProperties, BuildProperties buildProperties) {
    if (Objects.nonNull(gitProperties)) {
      shortCommitId = gitProperties.getShortCommitId();
    }

    if (Objects.nonNull(buildProperties)) {
      time = buildProperties.getTime();
      version = buildProperties.getVersion();
    }
    Object p = ObjectUtils.firstNonNull(shortCommitId, time, version, RandomStringUtils.randomAlphanumeric(12));

    if (p instanceof Instant) {
      return DateTimeFormatter.ISO_INSTANT.format((Instant) p);
    }
    return p.toString();
  }

  /** {@inheritDoc} */
  @Override
  public Object generate(Object target, Method method, Object... params) {
    return new PrefixedSimpleKey(prefix, method.getName(), params);
  }
}
