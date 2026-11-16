package br.com.reciclamais.model.dto;

import java.time.LocalDateTime;

public record ResgateResponseDTO(
        Integer resgateId,
        String codigoComprovante,
        String participanteNome,
        String cpfMascarado,
        String recompensaNome,
        Integer pontosUsados,
        Long saldoRestante,
        LocalDateTime dataHora) {
}
