package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception;

public class UserIsNotEnrolledInClassConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserIsNotEnrolledInClassConfigurationException() {
        super("The user is not enrolled in the class");}
}
