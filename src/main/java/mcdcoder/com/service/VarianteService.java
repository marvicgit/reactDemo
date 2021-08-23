package mcdcoder.com.service;

import java.util.Optional;
import mcdcoder.com.domain.Variante;
import mcdcoder.com.repository.VarianteRepository;
import mcdcoder.com.service.dto.VarianteDTO;
import mcdcoder.com.service.mapper.VarianteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Variante}.
 */
@Service
@Transactional
public class VarianteService {

    private final Logger log = LoggerFactory.getLogger(VarianteService.class);

    private final VarianteRepository varianteRepository;

    private final VarianteMapper varianteMapper;

    public VarianteService(VarianteRepository varianteRepository, VarianteMapper varianteMapper) {
        this.varianteRepository = varianteRepository;
        this.varianteMapper = varianteMapper;
    }

    /**
     * Save a variante.
     *
     * @param varianteDTO the entity to save.
     * @return the persisted entity.
     */
    public VarianteDTO save(VarianteDTO varianteDTO) {
        log.debug("Request to save Variante : {}", varianteDTO);
        Variante variante = varianteMapper.toEntity(varianteDTO);
        variante = varianteRepository.save(variante);
        return varianteMapper.toDto(variante);
    }

    /**
     * Partially update a variante.
     *
     * @param varianteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VarianteDTO> partialUpdate(VarianteDTO varianteDTO) {
        log.debug("Request to partially update Variante : {}", varianteDTO);

        return varianteRepository
            .findById(varianteDTO.getId())
            .map(
                existingVariante -> {
                    varianteMapper.partialUpdate(existingVariante, varianteDTO);

                    return existingVariante;
                }
            )
            .map(varianteRepository::save)
            .map(varianteMapper::toDto);
    }

    /**
     * Get all the variantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VarianteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Variantes");
        return varianteRepository.findAll(pageable).map(varianteMapper::toDto);
    }

    /**
     * Get one variante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VarianteDTO> findOne(Long id) {
        log.debug("Request to get Variante : {}", id);
        return varianteRepository.findById(id).map(varianteMapper::toDto);
    }

    /**
     * Delete the variante by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Variante : {}", id);
        varianteRepository.deleteById(id);
    }
}
