package br.com.reciclamais.controller;

import br.com.reciclamais.model.dto.UsuarioConsultaRequestDTO;
import br.com.reciclamais.model.dto.UsuarioIdentificacaoResponseDTO;
import br.com.reciclamais.model.dto.UsuarioRequestDTO;
import br.com.reciclamais.model.dto.UsuarioResponseDTO;
import br.com.reciclamais.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO cadastrar(@Valid @RequestBody UsuarioRequestDTO request) {
        return usuarioService.cadastrar(request);
    }

    @PostMapping("/identificar")
    public UsuarioIdentificacaoResponseDTO identificar(@Valid @RequestBody UsuarioConsultaRequestDTO request) {
        return usuarioService.identificarPorCpf(request.cpf());
    }
}
