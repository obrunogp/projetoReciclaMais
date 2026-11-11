package br.com.reciclamais.repository;

import br.com.reciclamais.model.entity.Coleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColetaRepository extends JpaRepository<Coleta, Integer> {
    List<Coleta> findByUsuarioIdOrderByDataHoraDesc(Integer usuarioId);
}
