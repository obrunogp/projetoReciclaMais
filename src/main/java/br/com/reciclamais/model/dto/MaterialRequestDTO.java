package br.com.reciclamais.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record MaterialRequestDTO(
        @NotBlank(message = "Informe o nome do material.")
        @Size(max = 50, message = "O nome do material deve ter no máximo 50 caracteres.") String nome,
        @NotNull(message = "Informe o tipo do material.") @Positive(message = "O ID do tipo deve ser positivo.") Integer tipoMaterialId,
        @NotNull(message = "Informe os pontos por quilo.")
        @PositiveOrZero(message = "Os pontos por quilo não podem ser negativos.")
        @Digits(integer = 4, fraction = 2, message = "A pontuação aceita até 4 dígitos inteiros e 2 decimais.")
        BigDecimal pontosPorKg) {
}
