package br.com.reciclamais.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "Informe o nome.")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.") String nome,
        @NotBlank(message = "Informe o CPF.")
        @Pattern(regexp = "(?:\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})", message = "Informe um CPF válido.") String cpf,
        @NotBlank(message = "Informe o e-mail.")
        @Email(message = "Informe um e-mail válido.")
        @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.") String email,
        @NotBlank(message = "Informe uma senha.")
        @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres.") String senha,
        @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres.") String endereco,
        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.") String telefone) {
}
