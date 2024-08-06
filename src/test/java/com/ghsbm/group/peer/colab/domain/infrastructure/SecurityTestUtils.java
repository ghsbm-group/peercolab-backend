package com.ghsbm.group.peer.colab.domain.infrastructure;

import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityTestUtils {
  public static void mockAdminUser() {
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn("admin");
    SecurityContextHolder.setContext(securityContext);
  }
}
