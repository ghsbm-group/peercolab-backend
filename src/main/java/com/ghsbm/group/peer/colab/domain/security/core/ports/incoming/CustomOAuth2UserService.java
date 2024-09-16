package com.ghsbm.group.peer.colab.domain.security.core.ports.incoming;

import com.ghsbm.group.peer.colab.application.config.Constants;
import com.ghsbm.group.peer.colab.application.config.oauth2.OAuth2AuthenticationProcessingException;
import com.ghsbm.group.peer.colab.application.config.oauth2.user.OAuth2UserInfo;
import com.ghsbm.group.peer.colab.application.config.oauth2.user.OAuth2UserInfoFactory;
import com.ghsbm.group.peer.colab.application.config.oauth2.user.UserPrincipal;
import com.ghsbm.group.peer.colab.domain.security.core.model.AuthProvider;
import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing.UserManagementRepository;
import com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants;
import com.ghsbm.group.peer.colab.infrastructure.RandomUtil;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserManagementRepository
      userManagementRepository; // todo change this with user management facade

  public CustomOAuth2UserService(UserManagementRepository userManagementRepository) {
    this.userManagementRepository = userManagementRepository;
  }

  @Override
  @Transactional
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
      throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      // Throwing an instance of AuthenticationException will trigger the
      // OAuth2AuthenticationFailureHandler
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    OAuth2UserInfo oAuth2UserInfo =
        OAuth2UserInfoFactory.getOAuth2UserInfo(
            oAuth2UserRequest.getClientRegistration().getRegistrationId(),
            oAuth2User.getAttributes());
    if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }

    Optional<User> userOptional =
        userManagementRepository.findOneByEmailIgnoreCase(oAuth2UserInfo.getEmail());

    User user;
    if (userOptional.isPresent()) {
      user = userOptional.get();
      if (!user.getProvider()
          .equals(
              AuthProvider.valueOf(
                  oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
        throw new OAuth2AuthenticationProcessingException(
            "Looks like you're signed up with "
                + user.getProvider()
                + " account. Please use your "
                + user.getProvider()
                + " account to login.");
      }
      user = updateExistingUser(user, oAuth2UserInfo);
    } else {
      user =
          registerNewUser(
              oAuth2UserRequest,
              oAuth2UserInfo); // todo change this with userManagementFacade.createUser
    }

    return UserPrincipal.create(user, oAuth2User.getAttributes());
  }

  private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    User user = new User();

    user.setProvider(
        AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
    user.setProviderId(oAuth2UserInfo.getId());
    user.setFirstName(oAuth2UserInfo.getName());
    user.setEmail(oAuth2UserInfo.getEmail());
    user.setPassword(RandomUtil.generate60CharRandomAlphanumericString());
    // mandatory in the db
    user.setLogin(oAuth2UserInfo.getEmail());
    user.setImageUrl(oAuth2UserInfo.getImageUrl());
    user.setActivated(true);
    user.setAuthorities(Set.of(new Authority(AuthoritiesConstants.USER)));
    user.setLangKey(Constants.DEFAULT_LANGUAGE);
    return userManagementRepository.persist(user);
  }

  private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
    existingUser.setFirstName(oAuth2UserInfo.getFirstName());
    existingUser.setLastName(oAuth2UserInfo.getLastName());
    existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
    return userManagementRepository.persist(existingUser);
  }
}
