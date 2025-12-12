package br.com.mba.spring.colegio.globalHandler.exeption;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
