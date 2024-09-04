package com.ghsbm.group.peer.colab.infrastructure.util;

import static com.ghsbm.group.peer.colab.infrastructure.SecurityUtils.AUTHORITIES_KEY;
import static com.ghsbm.group.peer.colab.infrastructure.SecurityUtils.JWT_ALGORITHM;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenProvider {

  private final JwtEncoder jwtEncoder;

  @Value("${peer.security.authentication.jwt.token-validity-in-seconds:0}")
  private long tokenValidityInSeconds;

  @Value("${peer.security.authentication.jwt.token-validity-in-seconds-for-remember-me:0}")
  private long tokenValidityInSecondsForRememberMe;

  public TokenProvider(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  public String createToken(Authentication authentication, boolean rememberMe) {
    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

    Instant now = Instant.now();
    Instant validity;
    if (rememberMe) {
      validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
    } else {
      validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
    }

    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .build();

    JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
  }
}
