package br.com.reciclamais.model.entity;

import java.math.BigDecimal;

public class Material {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valorPorKg;
    private Boolean ativo;

    public Material(String nome, String descricao, double peso, BigDecimal valorPorKg) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorPorKg = valorPorKg;
        this.ativo = true;
    }

    public boolean isAceito() {
        return Boolean.TRUE.equals(ativo);
    }

}