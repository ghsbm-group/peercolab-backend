package com.ghsbm.group.peer.colab.domain.file.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetails {
    private String login;
    private String firstName;
    private String lastName;
}
