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
import java.time.LocalDateTime;

@Entity
@Table(name = "resgate")
public class Resgate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "codigo_comprovante", nullable = false, unique = true, length = 24)
    private String codigoComprovante;

    @Column(name = "recompensa_codigo", nullable = false, length = 64)
    private String recompensaCodigo;

    @Column(name = "recompensa_nome", nullable = false, length = 180)
    private String recompensaNome;

    @Column(name = "pontos_gastos", nullable = false)
    private Integer pontosGastos;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    protected Resgate() {
    }

    public Resgate(Usuario usuario, String codigoComprovante, String recompensaCodigo,
                   String recompensaNome, Integer pontosGastos, LocalDateTime dataHora) {
        this.usuario = usuario;
        this.codigoComprovante = codigoComprovante;
        this.recompensaCodigo = recompensaCodigo;
        this.recompensaNome = recompensaNome;
        this.pontosGastos = pontosGastos;
        this.dataHora = dataHora;
    }

    public Integer getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public String getCodigoComprovante() { return codigoComprovante; }
    public String getRecompensaCodigo() { return recompensaCodigo; }
    public String getRecompensaNome() { return recompensaNome; }
    public Integer getPontosGastos() { return pontosGastos; }
    public LocalDateTime getDataHora() { return dataHora; }
}
