package br.com.reciclamais.model.dto;

import br.com.reciclamais.model.entity.TipoMaterial;

public record TipoMaterialResponseDTO(Integer id, String nome, String descricao) {
    public static TipoMaterialResponseDTO from(TipoMaterial tipo) {
        return new TipoMaterialResponseDTO(tipo.getId(), tipo.getNome(), tipo.getDescricao());
    }
}
