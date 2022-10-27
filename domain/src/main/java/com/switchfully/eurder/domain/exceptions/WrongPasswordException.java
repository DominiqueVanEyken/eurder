package com.switchfully.eurder.domain.exceptions;

public class WrongPasswordException extends IllegalArgumentException {
    public WrongPasswordException() {
        super("Wrong credentials");
    }
}
