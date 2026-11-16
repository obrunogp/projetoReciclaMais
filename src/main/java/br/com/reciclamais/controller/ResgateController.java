package br.com.reciclamais.controller;

import br.com.reciclamais.model.dto.RecompensaResponseDTO;
import br.com.reciclamais.model.dto.ResgateRequestDTO;
import br.com.reciclamais.model.dto.ResgateHistoricoResponseDTO;
import br.com.reciclamais.model.dto.ResgateResponseDTO;
import br.com.reciclamais.service.ResgateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ResgateController {

    private final ResgateService resgateService;

    public ResgateController(ResgateService resgateService) {
        this.resgateService = resgateService;
    }

    @GetMapping("/recompensas")
    public List<RecompensaResponseDTO> listarRecompensas() {
        return resgateService.listarRecompensas();
    }

    @GetMapping("/usuarios/{usuarioId}/resgates")
    public List<ResgateHistoricoResponseDTO> listarHistorico(@PathVariable Integer usuarioId) {
        return resgateService.listarHistorico(usuarioId);
    }

    @PostMapping("/usuarios/{usuarioId}/resgates")
    @ResponseStatus(HttpStatus.CREATED)
    public ResgateResponseDTO resgatar(@PathVariable Integer usuarioId,
                                       @Valid @RequestBody ResgateRequestDTO request) {
        return resgateService.resgatar(usuarioId, request.recompensaCodigo());
    }
}
