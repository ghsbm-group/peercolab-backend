package com.ghsbm.group.peer.colab.application.config.oauth2.user;

import java.util.Map;
import org.apache.commons.lang3.NotImplementedException;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {

  public GithubOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return ((Integer) attributes.get("id")).toString();
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }

  @Override
  public String getFirstName() {
    throw new NotImplementedException();
  }

  @Override
  public String getLastName() {
    throw new NotImplementedException();
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getImageUrl() {
    return (String) attributes.get("avatar_url");
  }
}
