package com.ghsbm.group.peer.colab.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to PeerColab.
 *
 * <p>Properties are configured in the {@code application.properties} file. See {@link
 * PeerProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {}
