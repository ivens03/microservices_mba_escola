package br.com.mba.spring.colegio.globalHandler.exeption;

public class AlunoNotFoundException extends RuntimeException {
    public AlunoNotFoundException(String message) {
        super(message);
    }
}
