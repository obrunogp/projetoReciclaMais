package br.com.reciclamais.model.entity;

import java.math.BigDecimal;

public class Material {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal pontosPorKg;
    private Boolean ativo;

    public Material(String nome, String descricao, BigDecimal pontosPorKg) {
        this.nome = nome;
        this.descricao = descricao;
        this.pontosPorKg = pontosPorKg;
        this.ativo = true;
    }

    public long getPontosPorKg() {
        return pontosPorKg;
    }

    public boolean isAceito() {
        return Boolean.TRUE.equals(ativo);
    }

}
