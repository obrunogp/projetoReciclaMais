package br.com.reciclamais.model.entity;

import java.time.LocalTime;

public class LocalColeta {

        private Long id;
        private String nome;
        private String endereco;
        private String cidade;
        private String estado;
        private String telefone;
        private LocalTime horarioAbertura;
        private LocalTime horarioFechamento;
        private Boolean ativo;

        public LocalColeta(String nome, String endereco, String cidade, String estado, String telefone, LocalTime horarioAbertura, LocalTime horarioFechamento) {
            this.nome = nome;
            this.endereco = endereco;
            this.cidade = cidade;
            this.estado = estado;
            this.telefone = telefone;
            this.horarioAbertura = horarioAbertura;
            this.horarioFechamento = horarioFechamento;
            this.ativo = true;
        }

        public boolean isAtivo() {
            return Boolean.TRUE.equals(ativo);
        }
}
