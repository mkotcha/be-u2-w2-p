package org.emmek.beu2w2p.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(long id) {
        super("id " + id + " not found!");
    }
}

