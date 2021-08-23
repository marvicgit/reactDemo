package mcdcoder.com.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mcdcoder.com.repository.ImagenRepository;
import mcdcoder.com.service.ImagenService;
import mcdcoder.com.service.dto.ImagenDTO;
import mcdcoder.com.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mcdcoder.com.domain.Imagen}.
 */
@RestController
@RequestMapping("/api")
public class ImagenResource {

    private final Logger log = LoggerFactory.getLogger(ImagenResource.class);

    private static final String ENTITY_NAME = "imagen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImagenService imagenService;

    private final ImagenRepository imagenRepository;

    public ImagenResource(ImagenService imagenService, ImagenRepository imagenRepository) {
        this.imagenService = imagenService;
        this.imagenRepository = imagenRepository;
    }

    /**
     * {@code POST  /imagens} : Create a new imagen.
     *
     * @param imagenDTO the imagenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imagenDTO, or with status {@code 400 (Bad Request)} if the imagen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/imagens")
    public ResponseEntity<ImagenDTO> createImagen(@Valid @RequestBody ImagenDTO imagenDTO) throws URISyntaxException {
        log.debug("REST request to save Imagen : {}", imagenDTO);
        if (imagenDTO.getId() != null) {
            throw new BadRequestAlertException("A new imagen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImagenDTO result = imagenService.save(imagenDTO);
        return ResponseEntity
            .created(new URI("/api/imagens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /imagens/:id} : Updates an existing imagen.
     *
     * @param id the id of the imagenDTO to save.
     * @param imagenDTO the imagenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagenDTO,
     * or with status {@code 400 (Bad Request)} if the imagenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imagenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/imagens/{id}")
    public ResponseEntity<ImagenDTO> updateImagen(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ImagenDTO imagenDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Imagen : {}, {}", id, imagenDTO);
        if (imagenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imagenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imagenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ImagenDTO result = imagenService.save(imagenDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imagenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /imagens/:id} : Partial updates given fields of an existing imagen, field will ignore if it is null
     *
     * @param id the id of the imagenDTO to save.
     * @param imagenDTO the imagenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagenDTO,
     * or with status {@code 400 (Bad Request)} if the imagenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the imagenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the imagenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/imagens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ImagenDTO> partialUpdateImagen(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ImagenDTO imagenDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Imagen partially : {}, {}", id, imagenDTO);
        if (imagenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imagenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imagenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImagenDTO> result = imagenService.partialUpdate(imagenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imagenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /imagens} : get all the imagens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imagens in body.
     */
    @GetMapping("/imagens")
    public ResponseEntity<List<ImagenDTO>> getAllImagens(Pageable pageable) {
        log.debug("REST request to get a page of Imagens");
        Page<ImagenDTO> page = imagenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /imagens/:id} : get the "id" imagen.
     *
     * @param id the id of the imagenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imagenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/imagens/{id}")
    public ResponseEntity<ImagenDTO> getImagen(@PathVariable Long id) {
        log.debug("REST request to get Imagen : {}", id);
        Optional<ImagenDTO> imagenDTO = imagenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imagenDTO);
    }

    /**
     * {@code DELETE  /imagens/:id} : delete the "id" imagen.
     *
     * @param id the id of the imagenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/imagens/{id}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Long id) {
        log.debug("REST request to delete Imagen : {}", id);
        imagenService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
