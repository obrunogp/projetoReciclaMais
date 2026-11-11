package br.com.reciclamais.service;

import br.com.reciclamais.exception.DadosInvalidosException;
import br.com.reciclamais.exception.RecursoNaoEncontradoException;
import br.com.reciclamais.model.dto.UsuarioIdentificacaoResponseDTO;
import br.com.reciclamais.model.dto.UsuarioRequestDTO;
import br.com.reciclamais.model.dto.UsuarioResponseDTO;
import br.com.reciclamais.model.entity.Usuario;
import br.com.reciclamais.repository.PontoUsuarioRepository;
import br.com.reciclamais.repository.UsuarioRepository;
import br.com.reciclamais.security.SenhaHasher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PontoUsuarioRepository pontoUsuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PontoUsuarioRepository pontoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pontoUsuarioRepository = pontoUsuarioRepository;
    }

    @Transactional(readOnly = true)
    public UsuarioIdentificacaoResponseDTO identificarPorCpf(String cpfInformado) {
        String numeros = cpfInformado.replaceAll("\\D", "");
        String cpfFormatado = numeros.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

        Usuario usuario = usuarioRepository.findByCpf(cpfFormatado)
                .or(() -> usuarioRepository.findByCpf(numeros))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participante não encontrado para esse CPF."));

        Long saldo = pontoUsuarioRepository.calcularSaldo(usuario.getId());
        String cpfMascarado = "***.***.***-" + numeros.substring(9);
        return new UsuarioIdentificacaoResponseDTO(
                usuario.getId(), usuario.getNome(), cpfMascarado, saldo == null ? 0L : saldo);
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO request) {
        String numerosCpf = request.cpf().replaceAll("\\D", "");
        if (!cpfValido(numerosCpf)) {
            throw new DadosInvalidosException("O CPF informado não é válido.");
        }
        String cpf = numerosCpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (usuarioRepository.existsByCpf(cpf) || usuarioRepository.existsByCpf(numerosCpf)) {
            throw new DadosInvalidosException("Já existe um usuário cadastrado com esse CPF.");
        }
        if (usuarioRepository.existsByEmail(email)) {
            throw new DadosInvalidosException("Já existe um usuário cadastrado com esse e-mail.");
        }

        Usuario usuario = new Usuario(
                request.nome().trim(), cpf, email, SenhaHasher.hash(request.senha()),
                limparOpcional(request.endereco()), limparOpcional(request.telefone()));
        return UsuarioResponseDTO.from(usuarioRepository.save(usuario));
    }

    private static String limparOpcional(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

    private static boolean cpfValido(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || cpf.chars().distinct().count() == 1) return false;
        int soma = 0;
        for (int i = 0; i < 9; i++) soma += (cpf.charAt(i) - '0') * (10 - i);
        int primeiro = (soma * 10) % 11;
        if (primeiro == 10) primeiro = 0;
        if (primeiro != cpf.charAt(9) - '0') return false;
        soma = 0;
        for (int i = 0; i < 10; i++) soma += (cpf.charAt(i) - '0') * (11 - i);
        int segundo = (soma * 10) % 11;
        if (segundo == 10) segundo = 0;
        return segundo == cpf.charAt(10) - '0';
    }
}
