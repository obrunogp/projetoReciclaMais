package br.com.reciclamais.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ColetaRequestDTO(
        @NotNull(message = "Informe o usuário.") @Positive(message = "O ID do usuário deve ser positivo.") Integer usuarioId,
        @NotNull(message = "Informe o material.") @Positive(message = "O ID do material deve ser positivo.") Integer materialId,
        @NotNull(message = "Informe o peso.") @Positive(message = "O peso deve ser maior que zero.")
        @Digits(integer = 8, fraction = 2, message = "O peso aceita até 8 dígitos inteiros e 2 decimais.") BigDecimal pesoKg) {
}
