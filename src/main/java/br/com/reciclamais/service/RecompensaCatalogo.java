package br.com.reciclamais.service;

import br.com.reciclamais.exception.DadosInvalidosException;
import br.com.reciclamais.model.dto.RecompensaResponseDTO;

import java.util.Arrays;
import java.util.List;

public enum RecompensaCatalogo {
    INGRESSO_CINEMA("Ingresso no cinema", 100),
    DESCONTO_IPTU_10("10% de desconto no IPTU", 1000),
    DESCONTO_INTERNET_20("20% de desconto na internet", 200),
    GASOLINA_10_LITROS("10 litros de gasolina", 1000),
    QUADRA_1_HORA("1 horário em quadras esportivas parceiras", 500),
    DESCONTO_FARMACIAS_20("20% de desconto em farmácias", 200);

    private final String nome;
    private final int pontos;

    RecompensaCatalogo(String nome, int pontos) {
        this.nome = nome;
        this.pontos = pontos;
    }

    public String getCodigo() { return name(); }
    public String getNome() { return nome; }
    public int getPontos() { return pontos; }

    public RecompensaResponseDTO toResponse() {
        return new RecompensaResponseDTO(name(), nome, pontos);
    }

    public static RecompensaCatalogo buscar(String codigo) {
        if (codigo == null) throw new DadosInvalidosException("Recompensa inválida.");
        return Arrays.stream(values())
                .filter(recompensa -> recompensa.name().equalsIgnoreCase(codigo.trim()))
                .findFirst()
                .orElseThrow(() -> new DadosInvalidosException("Recompensa inválida."));
    }

    public static List<RecompensaResponseDTO> listar() {
        return Arrays.stream(values()).map(RecompensaCatalogo::toResponse).toList();
    }
}
