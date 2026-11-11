package br.com.reciclamais.controller;

import br.com.reciclamais.model.dto.ColetaRequestDTO;
import br.com.reciclamais.model.dto.ColetaResponseDTO;
import br.com.reciclamais.service.ColetaService;
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
public class ColetaController {

    private final ColetaService coletaService;

    public ColetaController(ColetaService coletaService) {
        this.coletaService = coletaService;
    }

    @PostMapping("/coletas")
    @ResponseStatus(HttpStatus.CREATED)
    public ColetaResponseDTO registrar(@Valid @RequestBody ColetaRequestDTO request) {
        return coletaService.registrarColeta(request.usuarioId(), request.materialId(), request.pesoKg());
    }

    @GetMapping("/usuarios/{usuarioId}/coletas")
    public List<ColetaResponseDTO> historico(@PathVariable Integer usuarioId) {
        return coletaService.listarHistorico(usuarioId);
    }
}
