package br.com.loginauthapi.exception;

public class InvalidDataException extends RuntimeException{

    public InvalidDataException(String message) {
        super(message);
    }
}
