package br.com.reciclamais.repository;

import br.com.reciclamais.model.entity.Comprovante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComprovanteRepository extends JpaRepository<Comprovante, Integer> {
    Optional<Comprovante> findByColetaId(Integer coletaId);
}
