
package br.com.reciclamais.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Comprovante {

    private Long id;
    private String codigo;
    private Coleta coleta;
    private LocalDateTime dataEmissao;
    private BigDecimal valorTotal;
    private Integer pontosGerados;

    public Comprovante(String codigo, Coleta coleta,
                       BigDecimal valorTotal,
                       Integer pontosGerados) {
        this.codigo = codigo;
        this.coleta = coleta;
        this.dataEmissao = LocalDateTime.now();
        this.valorTotal = valorTotal;
        this.pontosGerados = pontosGerados;
    }
}

