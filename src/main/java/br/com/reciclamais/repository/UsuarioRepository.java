package br.com.reciclamais.repository;

import java.lang.ScopedValue;

public interface UsuarioRepository {
    <T> ScopedValue<T> findById(Long usuarioId);

    void save(Object usuario);
}
