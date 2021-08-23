package mcdcoder.com.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mcdcoder.com.repository.VarianteRepository;
import mcdcoder.com.service.VarianteService;
import mcdcoder.com.service.dto.VarianteDTO;
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
 * REST controller for managing {@link mcdcoder.com.domain.Variante}.
 */
@RestController
@RequestMapping("/api")
public class VarianteResource {

    private final Logger log = LoggerFactory.getLogger(VarianteResource.class);

    private static final String ENTITY_NAME = "variante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VarianteService varianteService;

    private final VarianteRepository varianteRepository;

    public VarianteResource(VarianteService varianteService, VarianteRepository varianteRepository) {
        this.varianteService = varianteService;
        this.varianteRepository = varianteRepository;
    }

    /**
     * {@code POST  /variantes} : Create a new variante.
     *
     * @param varianteDTO the varianteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new varianteDTO, or with status {@code 400 (Bad Request)} if the variante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/variantes")
    public ResponseEntity<VarianteDTO> createVariante(@Valid @RequestBody VarianteDTO varianteDTO) throws URISyntaxException {
        log.debug("REST request to save Variante : {}", varianteDTO);
        if (varianteDTO.getId() != null) {
            throw new BadRequestAlertException("A new variante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VarianteDTO result = varianteService.save(varianteDTO);
        return ResponseEntity
            .created(new URI("/api/variantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /variantes/:id} : Updates an existing variante.
     *
     * @param id the id of the varianteDTO to save.
     * @param varianteDTO the varianteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated varianteDTO,
     * or with status {@code 400 (Bad Request)} if the varianteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the varianteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/variantes/{id}")
    public ResponseEntity<VarianteDTO> updateVariante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VarianteDTO varianteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Variante : {}, {}", id, varianteDTO);
        if (varianteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, varianteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!varianteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VarianteDTO result = varianteService.save(varianteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, varianteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /variantes/:id} : Partial updates given fields of an existing variante, field will ignore if it is null
     *
     * @param id the id of the varianteDTO to save.
     * @param varianteDTO the varianteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated varianteDTO,
     * or with status {@code 400 (Bad Request)} if the varianteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the varianteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the varianteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/variantes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VarianteDTO> partialUpdateVariante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VarianteDTO varianteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Variante partially : {}, {}", id, varianteDTO);
        if (varianteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, varianteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!varianteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VarianteDTO> result = varianteService.partialUpdate(varianteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, varianteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /variantes} : get all the variantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of variantes in body.
     */
    @GetMapping("/variantes")
    public ResponseEntity<List<VarianteDTO>> getAllVariantes(Pageable pageable) {
        log.debug("REST request to get a page of Variantes");
        Page<VarianteDTO> page = varianteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /variantes/:id} : get the "id" variante.
     *
     * @param id the id of the varianteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the varianteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/variantes/{id}")
    public ResponseEntity<VarianteDTO> getVariante(@PathVariable Long id) {
        log.debug("REST request to get Variante : {}", id);
        Optional<VarianteDTO> varianteDTO = varianteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(varianteDTO);
    }

    /**
     * {@code DELETE  /variantes/:id} : delete the "id" variante.
     *
     * @param id the id of the varianteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/variantes/{id}")
    public ResponseEntity<Void> deleteVariante(@PathVariable Long id) {
        log.debug("REST request to delete Variante : {}", id);
        varianteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
