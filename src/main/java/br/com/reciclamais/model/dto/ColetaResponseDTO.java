package br.com.reciclamais.model.dto;

import br.com.reciclamais.model.entity.Coleta;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ColetaResponseDTO(
        Integer id,
        Integer usuarioId,
        Integer materialId,
        String materialNome,
        BigDecimal pesoKg,
        LocalDateTime dataHora,
        Integer pontosGerados) {

    public static ColetaResponseDTO from(Coleta coleta) {
        return new ColetaResponseDTO(
                coleta.getId(),
                coleta.getUsuario().getId(),
                coleta.getMaterial().getId(),
                coleta.getMaterial().getNome(),
                coleta.getPesoKg(),
                coleta.getDataHora(),
                coleta.getPontosGerados());
    }
}
