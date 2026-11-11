package br.com.reciclamais.model.dto;

public record UsuarioIdentificacaoResponseDTO(
        Integer usuarioId,
        String nome,
        String cpfMascarado,
        Long saldoPontos) {
}
