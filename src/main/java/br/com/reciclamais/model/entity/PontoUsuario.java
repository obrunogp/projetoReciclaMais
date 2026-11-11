package br.com.reciclamais.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ponto_usuario")
public class PontoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Integer pontos;

    @Column(name = "data_atualizacao", nullable = false, insertable = false, updatable = false)
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MotivoPontos motivo;

    protected PontoUsuario() {
    }

    public PontoUsuario(Usuario usuario, Integer pontos, MotivoPontos motivo) {
        this.usuario = usuario;
        this.pontos = pontos;
        this.motivo = motivo;
    }

    public Integer getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Integer getPontos() { return pontos; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public MotivoPontos getMotivo() { return motivo; }

    public enum MotivoPontos {
        COLETA, RESGATE, AJUSTE, ESTORNO
    }
}
