package br.com.reciclamais.service;

import br.com.reciclamais.exception.DadosInvalidosException;
import br.com.reciclamais.exception.RecursoNaoEncontradoException;
import br.com.reciclamais.model.dto.MaterialRequestDTO;
import br.com.reciclamais.model.dto.MaterialResponseDTO;
import br.com.reciclamais.model.entity.Material;
import br.com.reciclamais.model.entity.TipoMaterial;
import br.com.reciclamais.repository.MaterialRepository;
import br.com.reciclamais.repository.TipoMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final TipoMaterialRepository tipoMaterialRepository;

    public MaterialService(MaterialRepository materialRepository, TipoMaterialRepository tipoMaterialRepository) {
        this.materialRepository = materialRepository;
        this.tipoMaterialRepository = tipoMaterialRepository;
    }

    @Transactional(readOnly = true)
    public List<MaterialResponseDTO> listarAtivos() {
        return materialRepository.findByAtivoTrueOrderByNomeAsc().stream()
                .map(MaterialResponseDTO::from)
                .toList();
    }

    @Transactional
    public MaterialResponseDTO cadastrar(MaterialRequestDTO request) {
        String nome = request.nome().trim();
        if (materialRepository.existsByNomeIgnoreCase(nome)) {
            throw new DadosInvalidosException("Já existe um material cadastrado com esse nome.");
        }
        TipoMaterial tipo = tipoMaterialRepository.findById(request.tipoMaterialId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tipo de material não encontrado."));
        if (!Boolean.TRUE.equals(tipo.getAtivo())) {
            throw new DadosInvalidosException("O tipo de material está inativo.");
        }
        Material material = materialRepository.save(new Material(nome, tipo, request.pontosPorKg()));
        return MaterialResponseDTO.from(material);
    }
}
