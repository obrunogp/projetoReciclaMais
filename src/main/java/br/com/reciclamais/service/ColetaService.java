package br.com.reciclamais.service;

import br.com.reciclamais.model.entity.Coleta;
import br.com.reciclamais.model.entity.LocalColeta;
import br.com.reciclamais.model.entity.Material;
import br.com.reciclamais.model.entity.Usuario;
import br.com.reciclamais.repository.ColetaRepository;
import br.com.reciclamais.repository.MaterialRepository;
import br.com.reciclamais.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ColetaService {

    private final ColetaRepository coletaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MaterialRepository materialRepository;

    public ColetaService(ColetaRepository coletaRepository, UsuarioRepository usuarioRepository, MaterialRepository materialRepository) {
        this.coletaRepository = coletaRepository;
        this.usuarioRepository = usuarioRepository;
        this.materialRepository = materialRepository;
    }

    public BigDecimal calcularPontos(BigDecimal peso, Material material) {

        if (peso == null || peso.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Peso inválido.");
        }

        if (material == null || !material.isAceito()) {
            throw new IllegalArgumentException("Material não aceito.");
        }

        return peso.multiply(
                BigDecimal.valueOf(material.getPontosPorKg())
        );
    }

    @Transactional
    public Coleta registrarColeta(
            Long usuarioId,
            Long materialId,
            Long localColetaId,
            BigDecimal peso) {

        Usuario usuario = (Usuario) usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado."));

        Material material = (Material) materialRepository.findById(materialId)
                .orElseThrow(() ->
                        new RuntimeException("Material não encontrado."));


        LocalColeta localColeta = localColetaRepository.findById(localColetaId)
                .orElseThrow(() ->
                        new RuntimeException("Local de coleta não encontrado."));

        BigDecimal pontos = calcularPontos(peso, material);

        Coleta coleta = new Coleta(
                usuario,
                material,
                localColeta,
                peso
        );

        coleta.setPontosGerados(pontos);

        usuario.adicionarPontos(pontos);

        usuarioRepository.save(usuario);

        return coletaRepository.save(coleta);
    }


}


}
