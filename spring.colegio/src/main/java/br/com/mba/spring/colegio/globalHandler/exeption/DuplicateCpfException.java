package br.com.mba.spring.colegio.globalHandler.exeption;

public class DuplicateCpfException extends RuntimeException {
    public DuplicateCpfException(String message) {
        super(message);
    }
}