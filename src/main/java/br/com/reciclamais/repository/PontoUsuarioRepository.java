package br.com.reciclamais.repository;

import br.com.reciclamais.model.entity.PontoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PontoUsuarioRepository extends JpaRepository<PontoUsuario, Integer> {

    @Query("select coalesce(sum(p.pontos), 0) from PontoUsuario p where p.usuario.id = :usuarioId")
    Long calcularSaldo(@Param("usuarioId") Integer usuarioId);
}
