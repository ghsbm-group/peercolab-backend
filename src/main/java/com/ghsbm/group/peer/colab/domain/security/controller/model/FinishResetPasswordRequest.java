package com.ghsbm.group.peer.colab.domain.security.controller.model;

import lombok.Data;

/**
 * View Model object for storing the user's key and password.
 */
@Data
public class FinishResetPasswordRequest {
    private String key;
    private String newPassword;
}
