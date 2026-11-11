package br.com.reciclamais.controller;

import br.com.reciclamais.model.dto.TipoMaterialRequestDTO;
import br.com.reciclamais.model.dto.TipoMaterialResponseDTO;
import br.com.reciclamais.service.TipoMaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-materiais")
public class TipoMaterialController {

    private final TipoMaterialService tipoMaterialService;

    public TipoMaterialController(TipoMaterialService tipoMaterialService) {
        this.tipoMaterialService = tipoMaterialService;
    }

    @GetMapping
    public List<TipoMaterialResponseDTO> listarAtivos() {
        return tipoMaterialService.listarAtivos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoMaterialResponseDTO cadastrar(@Valid @RequestBody TipoMaterialRequestDTO request) {
        return tipoMaterialService.cadastrar(request);
    }
}
