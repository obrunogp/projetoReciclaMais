package br.com.reciclamais.model.dto;

import jakarta.validation.constraints.NotBlank;

public record ResgateRequestDTO(
        @NotBlank(message = "Selecione uma recompensa.") String recompensaCodigo) {
}
