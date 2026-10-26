package br.com.reciclamais.model.entity;

import java.time.LocalDateTime;

public class PontoUsuario {

    private long id;
    private Usuario usuario;
    private Integer saldoPontos;
    private LocalDateTime dataAtualizacao;

    public void adicionarPontos(Integer pontos) {
        if (pontos <= 0) {
            System.out.println("A quantidade deve ser positiva.");
            return;
        }
        this.saldoPontos += pontos;
        this.dataAtualizacao = LocalDateTime.now();
    }

}


