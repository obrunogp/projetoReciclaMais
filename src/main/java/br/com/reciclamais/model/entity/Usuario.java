package br.com.reciclamais.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Usuario {

    private Long id;
    private String nome;
    private String cpf;
    private Boolean ativo;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private BigDecimal pontos;

    public Usuario(String nome, String cpf, Boolean ativo, BigDecimal pontos, String email, String senha, LocalDateTime dataCadastro) {
        this.nome = nome;
        this.cpf = cpf;
        this.ativo = ativo;
        this.pontos = pontos;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }

    public void mostrarInformacoes() {
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Ativo: " + ativo);
        System.out.println("Email: " + email);
        System.out.println("Pontos: " + pontos);
        System.out.println("Data de Cadastro: " + dataCadastro);
    }

    public BigDecimal getPontos() {
        return pontos;
    }

    public void adicionarPontos(BigDecimal pontos) {
        this.pontos = this.pontos.add(pontos);
    }
}
