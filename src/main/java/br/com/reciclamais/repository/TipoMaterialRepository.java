package br.com.reciclamais.repository;

import br.com.reciclamais.model.entity.TipoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoMaterialRepository extends JpaRepository<TipoMaterial, Integer> {
    List<TipoMaterial> findByAtivoTrueOrderByNomeAsc();
    boolean existsByNomeIgnoreCase(String nome);
}
