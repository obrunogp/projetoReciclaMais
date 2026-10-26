
package br.com.reciclamais.model.entity;

import java.time.LocalDateTime;

public class Coleta {

    private Long id;
    private Usuario usuario;
    private Material material;
    private LocalColeta localColeta;
    private Double peso;
    private LocalDateTime dataColeta;

    public Coleta(Usuario usuario, Material material,
                  LocalColeta localColeta, Double peso) {
        this.usuario = usuario;
        this.material = material;
        this.localColeta = localColeta;
        this.peso = peso;
        this.dataColeta = LocalDateTime.now();
    }
}

