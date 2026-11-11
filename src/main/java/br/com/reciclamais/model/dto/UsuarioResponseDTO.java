package br.com.reciclamais.model.dto;

import br.com.reciclamais.model.entity.Usuario;

public record UsuarioResponseDTO(Integer id, String nome, String cpf, String email) {
    public static UsuarioResponseDTO from(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail());
    }
}
