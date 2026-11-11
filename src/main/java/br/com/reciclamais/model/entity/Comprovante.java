package br.com.reciclamais.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "comprovante", uniqueConstraints = @UniqueConstraint(columnNames = "coleta_id"))
public class Comprovante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coleta_id", nullable = false, unique = true)
    private Coleta coleta;

    @Column(name = "data_hora", nullable = false, insertable = false, updatable = false)
    private LocalDateTime dataHora;

    @Column(name = "peso_total_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal pesoTotalKg;

    @Column(nullable = false)
    private Integer pontos = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(columnDefinition = "text")
    private String detalhes;

    protected Comprovante() {
    }

    public Comprovante(Coleta coleta, Usuario usuario, BigDecimal pesoTotalKg, Integer pontos, String detalhes) {
        this.coleta = coleta;
        this.usuario = usuario;
        this.pesoTotalKg = pesoTotalKg;
        this.pontos = pontos;
        this.detalhes = detalhes;
    }

    public Integer getId() { return id; }
    public Coleta getColeta() { return coleta; }
    public LocalDateTime getDataHora() { return dataHora; }
    public BigDecimal getPesoTotalKg() { return pesoTotalKg; }
    public Integer getPontos() { return pontos; }
    public Usuario getUsuario() { return usuario; }
    public String getDetalhes() { return detalhes; }
}
