package com.ghsbm.group.peer.colab.domain.security.controller.model;

import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.AdminUserDTO;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * View Model extending the AdminUserDTO, which is meant to be used in the user management UI.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RegisterUserRequest extends AdminUserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Override
    public String toString() {
        return "RegisterUserRequest{" + super.toString() + "} ";
    }
}
