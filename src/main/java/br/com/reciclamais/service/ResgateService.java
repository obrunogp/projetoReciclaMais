package br.com.reciclamais.service;

import br.com.reciclamais.exception.DadosInvalidosException;
import br.com.reciclamais.exception.RecursoNaoEncontradoException;
import br.com.reciclamais.model.dto.RecompensaResponseDTO;
import br.com.reciclamais.model.dto.ResgateResponseDTO;
import br.com.reciclamais.model.dto.ResgateHistoricoResponseDTO;
import br.com.reciclamais.model.entity.PontoUsuario;
import br.com.reciclamais.model.entity.Resgate;
import br.com.reciclamais.model.entity.Usuario;
import br.com.reciclamais.repository.PontoUsuarioRepository;
import br.com.reciclamais.repository.ResgateRepository;
import br.com.reciclamais.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class ResgateService {

    private final UsuarioRepository usuarioRepository;
    private final PontoUsuarioRepository pontoUsuarioRepository;
    private final ResgateRepository resgateRepository;

    public ResgateService(UsuarioRepository usuarioRepository,
                          PontoUsuarioRepository pontoUsuarioRepository,
                          ResgateRepository resgateRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pontoUsuarioRepository = pontoUsuarioRepository;
        this.resgateRepository = resgateRepository;
    }

    public List<RecompensaResponseDTO> listarRecompensas() {
        return RecompensaCatalogo.listar();
    }

    @Transactional(readOnly = true)
    public List<ResgateHistoricoResponseDTO> listarHistorico(Integer usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");
        }
        return resgateRepository.listarPorUsuario(usuarioId).stream()
                .map(resgate -> new ResgateHistoricoResponseDTO(
                        resgate.getCodigoComprovante(), resgate.getRecompensaNome(),
                        resgate.getPontosGastos(), resgate.getDataHora()))
                .toList();
    }

    @Transactional
    public ResgateResponseDTO resgatar(Integer usuarioId, String recompensaCodigo) {
        Usuario usuario = usuarioRepository.findByIdForUpdate(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
        RecompensaCatalogo recompensa = RecompensaCatalogo.buscar(recompensaCodigo);
        long saldoAtual = pontoUsuarioRepository.calcularSaldo(usuarioId);
        if (saldoAtual < recompensa.getPontos()) {
            throw new DadosInvalidosException("Saldo insuficiente. Disponível: " + saldoAtual
                    + " pontos; necessários: " + recompensa.getPontos() + ".");
        }

        String digitosCpf = usuario.getCpf().replaceAll("\\D", "");
        String cpfMascarado = "***.***.***-" + digitosCpf.substring(digitosCpf.length() - 2);
        String codigoComprovante = "RC-" + UUID.randomUUID().toString()
                .substring(0, 8).toUpperCase(Locale.ROOT);
        LocalDateTime dataHora = LocalDateTime.now();
        Resgate resgate = resgateRepository.save(new Resgate(
                usuario, codigoComprovante, recompensa.getCodigo(), recompensa.getNome(),
                recompensa.getPontos(), dataHora));
        pontoUsuarioRepository.save(new PontoUsuario(
                usuario, -recompensa.getPontos(), PontoUsuario.MotivoPontos.RESGATE));

        return new ResgateResponseDTO(
                resgate.getId(), codigoComprovante, usuario.getNome(), cpfMascarado,
                recompensa.getNome(), recompensa.getPontos(), saldoAtual - recompensa.getPontos(), dataHora);
    }
}
