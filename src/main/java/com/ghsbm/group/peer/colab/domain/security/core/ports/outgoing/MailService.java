package com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.security.core.model.User;

public interface MailService {

  void sendPasswordResetMail(User u);

  void sendActivationEmail(User newUser);

  void sendCreationEmail(User user);
}
