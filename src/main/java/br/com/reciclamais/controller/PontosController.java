package br.com.reciclamais.controller;

import br.com.reciclamais.model.dto.SaldoPontosResponseDTO;
import br.com.reciclamais.service.PontosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios/{usuarioId}/pontos")
public class PontosController {

    private final PontosService pontosService;

    public PontosController(PontosService pontosService) {
        this.pontosService = pontosService;
    }

    @GetMapping
    public SaldoPontosResponseDTO consultarSaldo(@PathVariable Integer usuarioId) {
        return pontosService.consultarSaldo(usuarioId);
    }
}
