
package br.com.reciclamais.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Coleta {

    private Long id;
    private Usuario usuario;
    private Material material;
    private LocalColeta localColeta;
    private BigDecimal peso;
    private LocalDateTime dataColeta;
    private BigDecimal pontosGerados;

    public Coleta(Usuario usuario, Material material, LocalColeta localColeta, BigDecimal peso) {
        this.usuario = usuario;
        this.material = material;
        this.localColeta = localColeta;
        this.peso = peso;
        this.dataColeta = LocalDateTime.now();
    }


    public void setPontosGerados(BigDecimal pontos) {
        this.pontosGerados = pontos;
    }
}



