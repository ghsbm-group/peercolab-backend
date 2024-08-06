package com.ghsbm.group.peer.colab.application.config;

import com.ghsbm.group.peer.colab.application.config.logging.LoggingAspect;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

  @Bean
  @Profile("dev")
  public LoggingAspect loggingAspect(Environment env) {
    return new LoggingAspect(env);
  }
}
