package com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.exception;

public class UsernameAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException() {
        super("Login name already used!");
    }
}
