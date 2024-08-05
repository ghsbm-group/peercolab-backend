package com.ghsbm.group.peer.colab.domain.classes.controller.errors;

import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class FolderAlreadyExistsException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public FolderAlreadyExistsException() {
        super("Folder already exists!", "folderManagement", "folderexists");
    }
}
