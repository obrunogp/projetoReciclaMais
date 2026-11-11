package br.com.reciclamais.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioConsultaRequestDTO(
        @NotBlank(message = "Informe o CPF do participante.")
        @Pattern(
                regexp = "(?:\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})",
                message = "Digite um CPF válido com 11 números.")
        String cpf) {
}
