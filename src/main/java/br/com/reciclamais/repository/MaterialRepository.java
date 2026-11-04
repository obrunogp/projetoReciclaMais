package br.com.reciclamais.repository;

import java.lang.ScopedValue;

public interface MaterialRepository {
    <T> ScopedValue<T> findById(Long materialId);
}
