package br.com.reciclamais.service;

import br.com.reciclamais.exception.DadosInvalidosException;
import br.com.reciclamais.exception.RecursoNaoEncontradoException;
import br.com.reciclamais.model.entity.Coleta;
import br.com.reciclamais.model.entity.Material;
import br.com.reciclamais.model.entity.PontoUsuario;
import br.com.reciclamais.model.entity.Usuario;
import br.com.reciclamais.model.dto.ColetaResponseDTO;
import br.com.reciclamais.repository.ColetaRepository;
import br.com.reciclamais.repository.MaterialRepository;
import br.com.reciclamais.repository.PontoUsuarioRepository;
import br.com.reciclamais.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ColetaService {

    private final ColetaRepository coletaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MaterialRepository materialRepository;
    private final PontoUsuarioRepository pontoUsuarioRepository;

    public ColetaService(
            ColetaRepository coletaRepository,
            UsuarioRepository usuarioRepository,
            MaterialRepository materialRepository,
            PontoUsuarioRepository pontoUsuarioRepository) {
        this.coletaRepository = coletaRepository;
        this.usuarioRepository = usuarioRepository;
        this.materialRepository = materialRepository;
        this.pontoUsuarioRepository = pontoUsuarioRepository;
    }

    public int calcularPontos(BigDecimal pesoKg, Material material) {
        if (pesoKg == null || pesoKg.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadosInvalidosException("O peso deve ser maior que zero.");
        }
        if (material == null || !material.isAceito()) {
            throw new DadosInvalidosException("Material não encontrado ou inativo.");
        }

        try {
            return pesoKg.multiply(material.getPontosPorKg())
                    .setScale(0, RoundingMode.HALF_UP)
                    .intValueExact();
        } catch (ArithmeticException ex) {
            throw new DadosInvalidosException("O cálculo de pontos excede o limite permitido.");
        }
    }

    @Transactional
    public ColetaResponseDTO registrarColeta(Integer usuarioId, Integer materialId, BigDecimal pesoKg) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Material não encontrado."));

        int pontos = calcularPontos(pesoKg, material);
        Coleta coleta = coletaRepository.save(new Coleta(usuario, material, pesoKg, pontos));

        // ponto_usuario é um extrato: o saldo é a soma dos lançamentos.
        // O banco proíbe lançamentos iguais a zero.
        if (pontos > 0) {
            pontoUsuarioRepository.save(new PontoUsuario(
                    usuario, pontos, PontoUsuario.MotivoPontos.COLETA));
        }

        return ColetaResponseDTO.from(coleta);
    }

    @Transactional(readOnly = true)
    public List<ColetaResponseDTO> listarHistorico(Integer usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");
        }
        return coletaRepository.findByUsuarioIdOrderByDataHoraDesc(usuarioId).stream()
                .map(ColetaResponseDTO::from)
                .toList();
    }
}
