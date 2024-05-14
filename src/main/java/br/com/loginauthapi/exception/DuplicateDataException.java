package br.com.loginauthapi.exception;

public class DuplicateDataException extends RuntimeException{

    public DuplicateDataException(String message) {
        super(message);
    }
}
