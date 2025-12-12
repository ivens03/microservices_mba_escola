package br.com.mba.spring.colegio.globalHandler.exeption;

public class NotaNotFoundException extends RuntimeException {
    public NotaNotFoundException(String message) {
        super(message);
    }
}
