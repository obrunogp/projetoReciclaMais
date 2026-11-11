package br.com.reciclamais.service;

import br.com.reciclamais.exception.DadosInvalidosException;
import br.com.reciclamais.model.dto.TipoMaterialRequestDTO;
import br.com.reciclamais.model.dto.TipoMaterialResponseDTO;
import br.com.reciclamais.model.entity.TipoMaterial;
import br.com.reciclamais.repository.TipoMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoMaterialService {

    private final TipoMaterialRepository tipoMaterialRepository;

    public TipoMaterialService(TipoMaterialRepository tipoMaterialRepository) {
        this.tipoMaterialRepository = tipoMaterialRepository;
    }

    @Transactional(readOnly = true)
    public List<TipoMaterialResponseDTO> listarAtivos() {
        return tipoMaterialRepository.findByAtivoTrueOrderByNomeAsc().stream()
                .map(TipoMaterialResponseDTO::from)
                .toList();
    }

    @Transactional
    public TipoMaterialResponseDTO cadastrar(TipoMaterialRequestDTO request) {
        String nome = request.nome().trim();
        if (tipoMaterialRepository.existsByNomeIgnoreCase(nome)) {
            throw new DadosInvalidosException("Já existe um tipo de material cadastrado com esse nome.");
        }
        String descricao = request.descricao() == null || request.descricao().isBlank()
                ? null : request.descricao().trim();
        return TipoMaterialResponseDTO.from(tipoMaterialRepository.save(new TipoMaterial(nome, descricao)));
    }
}
