package br.com.mba.spring.colegio.globalHandler.exeption;

public class ProfessorNotFoundException extends RuntimeException {
    public ProfessorNotFoundException(String message) {
        super(message);
    }
}
