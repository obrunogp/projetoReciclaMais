package br.com.reciclamais.model.dto;

import java.time.LocalDateTime;

public record ResgateHistoricoResponseDTO(
        String codigoComprovante,
        String recompensaNome,
        Integer pontosUsados,
        LocalDateTime dataHora) {
}
