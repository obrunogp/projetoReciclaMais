package br.com.reciclamais.repository;

import br.com.reciclamais.model.entity.Resgate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResgateRepository extends JpaRepository<Resgate, Integer> {

    @Query("select r from Resgate r where r.usuario.id = :usuarioId order by r.dataHora desc")
    List<Resgate> listarPorUsuario(@Param("usuarioId") Integer usuarioId);
}
