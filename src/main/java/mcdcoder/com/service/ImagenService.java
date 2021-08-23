package mcdcoder.com.service;

import java.util.Optional;
import mcdcoder.com.domain.Imagen;
import mcdcoder.com.repository.ImagenRepository;
import mcdcoder.com.service.dto.ImagenDTO;
import mcdcoder.com.service.mapper.ImagenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Imagen}.
 */
@Service
@Transactional
public class ImagenService {

    private final Logger log = LoggerFactory.getLogger(ImagenService.class);

    private final ImagenRepository imagenRepository;

    private final ImagenMapper imagenMapper;

    public ImagenService(ImagenRepository imagenRepository, ImagenMapper imagenMapper) {
        this.imagenRepository = imagenRepository;
        this.imagenMapper = imagenMapper;
    }

    /**
     * Save a imagen.
     *
     * @param imagenDTO the entity to save.
     * @return the persisted entity.
     */
    public ImagenDTO save(ImagenDTO imagenDTO) {
        log.debug("Request to save Imagen : {}", imagenDTO);
        Imagen imagen = imagenMapper.toEntity(imagenDTO);
        imagen = imagenRepository.save(imagen);
        return imagenMapper.toDto(imagen);
    }

    /**
     * Partially update a imagen.
     *
     * @param imagenDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ImagenDTO> partialUpdate(ImagenDTO imagenDTO) {
        log.debug("Request to partially update Imagen : {}", imagenDTO);

        return imagenRepository
            .findById(imagenDTO.getId())
            .map(
                existingImagen -> {
                    imagenMapper.partialUpdate(existingImagen, imagenDTO);

                    return existingImagen;
                }
            )
            .map(imagenRepository::save)
            .map(imagenMapper::toDto);
    }

    /**
     * Get all the imagens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ImagenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Imagens");
        return imagenRepository.findAll(pageable).map(imagenMapper::toDto);
    }

    /**
     * Get one imagen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImagenDTO> findOne(Long id) {
        log.debug("Request to get Imagen : {}", id);
        return imagenRepository.findById(id).map(imagenMapper::toDto);
    }

    /**
     * Delete the imagen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Imagen : {}", id);
        imagenRepository.deleteById(id);
    }
}
