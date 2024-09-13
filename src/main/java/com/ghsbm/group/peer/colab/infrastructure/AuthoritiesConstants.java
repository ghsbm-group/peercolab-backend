package com.ghsbm.group.peer.colab.infrastructure;

/** Constants for Spring Security authorities. */
public final class AuthoritiesConstants {

  public static final String ADMIN = "ROLE_ADMIN";

  public static final String STUDENT_ADMIN = "ROLE_STUDENT_ADMIN";

  public static final String USER = "ROLE_USER";

  public static final String ANONYMOUS = "ROLE_ANONYMOUS";

  public static final String USER_MUST_BE_LOGGED_IN = "User must be logged in";

  public static final String DELETED_USER_NAME = "anonymous";

  private AuthoritiesConstants() {}
}
