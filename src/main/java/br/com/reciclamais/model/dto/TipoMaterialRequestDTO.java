package br.com.reciclamais.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoMaterialRequestDTO(
        @NotBlank(message = "Informe o nome do tipo de material.")
        @Size(max = 50, message = "O nome do tipo deve ter no máximo 50 caracteres.") String nome,
        String descricao) {
}
