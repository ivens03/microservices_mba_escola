package br.com.mba.spring.colegio.globalHandler.exeption;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
