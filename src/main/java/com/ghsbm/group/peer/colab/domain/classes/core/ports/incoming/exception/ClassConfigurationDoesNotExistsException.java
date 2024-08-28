package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception;

public class ClassConfigurationDoesNotExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClassConfigurationDoesNotExistsException() {
        super("Class does not exists!");}
    }
