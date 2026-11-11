package br.com.reciclamais.model.dto;

import br.com.reciclamais.model.entity.Material;
import java.math.BigDecimal;

public record MaterialResponseDTO(
        Integer id,
        String nome,
        String tipo,
        BigDecimal pontosPorKg) {

    public static MaterialResponseDTO from(Material material) {
        return new MaterialResponseDTO(
                material.getId(),
                material.getNome(),
                material.getTipoMaterial().getNome(),
                material.getPontosPorKg());
    }
}
