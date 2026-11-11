package br.com.reciclamais.service;

import br.com.reciclamais.exception.RecursoNaoEncontradoException;
import br.com.reciclamais.model.dto.SaldoPontosResponseDTO;
import br.com.reciclamais.repository.PontoUsuarioRepository;
import br.com.reciclamais.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PontosService {

    private final PontoUsuarioRepository pontoUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    public PontosService(PontoUsuarioRepository pontoUsuarioRepository, UsuarioRepository usuarioRepository) {
        this.pontoUsuarioRepository = pontoUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public SaldoPontosResponseDTO consultarSaldo(Integer usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");
        }
        Long saldo = pontoUsuarioRepository.calcularSaldo(usuarioId);
        return new SaldoPontosResponseDTO(usuarioId, saldo == null ? 0L : saldo);
    }
}
