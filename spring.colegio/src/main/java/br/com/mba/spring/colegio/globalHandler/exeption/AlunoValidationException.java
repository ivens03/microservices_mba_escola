package br.com.mba.spring.colegio.globalHandler.exeption;

public class AlunoValidationException extends RuntimeException {
    public AlunoValidationException(String message) {
        super(message);
    }
}
