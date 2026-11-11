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
import java.time.LocalDateTime;

@Entity
@Table(name = "coleta")
public class Coleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(name = "peso_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal pesoKg;

    @Column(name = "data_hora", nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @Column(name = "pontos_gerados", nullable = false)
    private Integer pontosGerados = 0;

    protected Coleta() {
    }

    public Coleta(Usuario usuario, Material material, BigDecimal pesoKg, Integer pontosGerados) {
        this.usuario = usuario;
        this.material = material;
        this.pesoKg = pesoKg;
        this.pontosGerados = pontosGerados;
        this.dataHora = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Material getMaterial() { return material; }
    public BigDecimal getPesoKg() { return pesoKg; }
    public LocalDateTime getDataHora() { return dataHora; }
    public Integer getPontosGerados() { return pontosGerados; }
}
