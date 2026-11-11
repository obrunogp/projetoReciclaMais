package br.com.reciclamais.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_material_id", nullable = false)
    private TipoMaterial tipoMaterial;

    @Column(name = "pontos_por_kg", nullable = false, precision = 6, scale = 2)
    private BigDecimal pontosPorKg;

    @Column(nullable = false)
    private Boolean ativo = true;

    protected Material() {
    }

    public Material(String nome, TipoMaterial tipoMaterial, BigDecimal pontosPorKg) {
        this.nome = nome;
        this.tipoMaterial = tipoMaterial;
        this.pontosPorKg = pontosPorKg;
        this.ativo = true;
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public TipoMaterial getTipoMaterial() { return tipoMaterial; }
    public BigDecimal getPontosPorKg() { return pontosPorKg; }
    public Boolean getAtivo() { return ativo; }

    public boolean isAceito() {
        return Boolean.TRUE.equals(ativo);
    }
}
