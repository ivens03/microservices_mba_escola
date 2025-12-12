package br.com.mba.spring.colegio.globalHandler.exeption;

public class DisciplinaNotFoundException extends RuntimeException {
    public DisciplinaNotFoundException(String message) {
        super(message);
    }
}
