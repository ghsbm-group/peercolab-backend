package com.ghsbm.group.peer.colab.infrastructure.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ProblemDetail;

public class ProblemDetailWithCause extends ProblemDetail {
  private ProblemDetailWithCause cause;

  ProblemDetailWithCause(int rawStatus) {
    super(rawStatus);
  }

  ProblemDetailWithCause(int rawStatus, ProblemDetailWithCause cause) {
    super(rawStatus);
    this.cause = cause;
  }

  public ProblemDetailWithCause getCause() {
    return cause;
  }

  public void setCause(ProblemDetailWithCause cause) {
    this.cause = cause;
  }

  // The missing builder from Spring
  public static class ProblemDetailWithCauseBuilder {
    private String title;
    private int status;
    private String detail;
    private Map<String, Object> properties = new HashMap<>();
    private ProblemDetailWithCause cause;

    public static ProblemDetailWithCauseBuilder instance(){
      return new ProblemDetailWithCauseBuilder();
    }

    public ProblemDetailWithCauseBuilder withTitle(String title) {
      this.title = title;
      return this;
    }

    public ProblemDetailWithCauseBuilder withStatus(int status) {
      this.status = status;
      return this;
    }

    public ProblemDetailWithCauseBuilder withDetail(String detail) {
      this.detail = detail;
      return this;
    }

    public ProblemDetailWithCauseBuilder withCause(ProblemDetailWithCause cause) {
      this.cause = cause;
      return this;
    }

    public ProblemDetailWithCauseBuilder withProperties(Map<String, Object> properties) {
      this.properties = properties;
      return this;
    }

    public ProblemDetailWithCauseBuilder withProperty(String key, Object value) {
      this.properties.put(key, value);
      return this;
    }

    public ProblemDetailWithCause build() {
      ProblemDetailWithCause cause = new ProblemDetailWithCause(this.status);
      cause.setTitle(this.title);
      cause.setDetail(this.detail);
      this.properties.forEach(cause::setProperty);
      cause.setCause(this.cause);
      return cause;
    }
  }
}
