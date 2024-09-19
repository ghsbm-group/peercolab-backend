package com.ghsbm.group.peer.colab.application.config;

import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.*;

import com.ghsbm.group.peer.colab.application.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ghsbm.group.peer.colab.application.config.oauth2.OAuth2AuthenticationFailureHandler;
import com.ghsbm.group.peer.colab.application.config.oauth2.OAuth2AuthenticationSuccessHandler;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.CustomOAuth2UserService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired private CustomOAuth2UserService customOAuth2UserService;

  @Autowired private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

  @Autowired private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
  private final PeerProperties peerProperties;

  public SecurityConfiguration(PeerProperties peerProperties) {
    this.peerProperties = peerProperties;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc)
      throws Exception {
    http.logout(
            (logout) -> logout.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
        .cors(cors -> Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authz ->
                // prettier-ignore
                authz
                    .requestMatchers(mvc.pattern("/swagger-ui/**"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/swagger-ui"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/api/authenticate"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/api/register"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/api/activate"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/api/account/reset-password/init"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/api/account/reset-password/finish"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/api/admin/**"))
                    .hasAuthority(ADMIN)
                    .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/all-authority-requests"))
                    .hasAuthority(ADMIN)
                    .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/approve-authority"))
                    .hasAuthority(ADMIN)
                    .requestMatchers(mvc.pattern("/api/**"))
                    .authenticated()
                    .requestMatchers(mvc.pattern("/api-docs/**"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/management/health"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/management/health/**"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/management/info"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/management/prometheus"))
                    .permitAll()
                    .requestMatchers(mvc.pattern("/management/**"))
                    .hasAuthority(ADMIN)
                    .requestMatchers(mvc.pattern(HttpMethod.POST, "chat/**"))
                    .hasAnyAuthority(STUDENT, STUDENT_ADMIN, ADMIN)
                    .requestMatchers(mvc.pattern(HttpMethod.GET, "chat/**"))
                    .hasAnyAuthority(STUDENT, STUDENT_ADMIN, ADMIN)
                    .requestMatchers(mvc.pattern(HttpMethod.POST, "classes/**"))
                    .hasAnyAuthority(ADMIN, STUDENT_ADMIN)
                    .requestMatchers(mvc.pattern(HttpMethod.GET, "classes/enrolment-key"))
                    .hasAnyAuthority(ADMIN, STUDENT_ADMIN)
                    .requestMatchers(mvc.pattern(HttpMethod.GET, "classes/**"))
                    .hasAnyAuthority(ADMIN, STUDENT_ADMIN, STUDENT)
                    .requestMatchers(mvc.pattern(HttpMethod.POST, "schools/**"))
                    .hasAnyAuthority(ADMIN, STUDENT_ADMIN)
                    .requestMatchers(mvc.pattern("file/**"))
                    .hasAnyAuthority(STUDENT_ADMIN, ADMIN, STUDENT)
                    .requestMatchers(mvc.pattern(HttpMethod.POST, "classes/enrol"))
                    .permitAll()
                    .requestMatchers(mvc.pattern(HttpMethod.GET, "schools/**"))
                    .authenticated()
                    .requestMatchers(mvc.pattern(HttpMethod.GET, "/oauth2/**"))
                    .permitAll())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(
            exceptions ->
                exceptions
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .oauth2Login(
            o ->
                o.authorizationEndpoint(
                        a ->
                            a.baseUri("/oauth2/authorize")
                                .authorizationRequestRepository(
                                    cookieAuthorizationRequestRepository()))
                    .redirectionEndpoint(r -> r.baseUri("/oauth2/callback/*"))
                    .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler));
    return http.build();
  }

  /*
    By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
    the authorization request. But, since our service is stateless, we can't save it in
    the session. We'll save the request in a Base64 encoded cookie instead.
  */
  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }

  @Bean
  MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    return new MvcRequestMatcher.Builder(introspector);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(peerProperties.allowedOrigins));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(Arrays.asList("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
