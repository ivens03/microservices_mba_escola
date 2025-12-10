package br.com.mba.spring.colegio.usuarios.interfaces;

public interface CreateEntityInterface<T, D> {
    T create(D dto);
}
