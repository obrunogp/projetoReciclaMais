package br.com.reciclamais.repository;

import br.com.reciclamais.model.entity.Material;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @EntityGraph(attributePaths = "tipoMaterial")
    List<Material> findByAtivoTrueOrderByNomeAsc();
    boolean existsByNomeIgnoreCase(String nome);
}
