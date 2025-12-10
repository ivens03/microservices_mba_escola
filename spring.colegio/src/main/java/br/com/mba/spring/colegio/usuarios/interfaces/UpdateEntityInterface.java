package br.com.mba.spring.colegio.usuarios.interfaces;

public interface UpdateEntityInterface<T, D> {
    T update(Long id, D dto);
}
