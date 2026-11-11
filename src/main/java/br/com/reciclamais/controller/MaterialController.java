package br.com.reciclamais.controller;

import br.com.reciclamais.model.dto.MaterialResponseDTO;
import br.com.reciclamais.model.dto.MaterialRequestDTO;
import br.com.reciclamais.service.MaterialService;
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
@RequestMapping("/api/materiais")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public List<MaterialResponseDTO> listarAtivos() {
        return materialService.listarAtivos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialResponseDTO cadastrar(@Valid @RequestBody MaterialRequestDTO request) {
        return materialService.cadastrar(request);
    }
}
