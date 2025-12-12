package br.com.mba.spring.colegio.globalHandler.exeption;

public class ResponsavelAlunoNotFoundException extends RuntimeException {
    public ResponsavelAlunoNotFoundException(String message) {
        super(message);
    }
}
