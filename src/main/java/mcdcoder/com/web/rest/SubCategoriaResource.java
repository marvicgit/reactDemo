package mcdcoder.com.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mcdcoder.com.repository.SubCategoriaRepository;
import mcdcoder.com.service.SubCategoriaService;
import mcdcoder.com.service.dto.SubCategoriaDTO;
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
 * REST controller for managing {@link mcdcoder.com.domain.SubCategoria}.
 */
@RestController
@RequestMapping("/api")
public class SubCategoriaResource {

    private final Logger log = LoggerFactory.getLogger(SubCategoriaResource.class);

    private static final String ENTITY_NAME = "subCategoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubCategoriaService subCategoriaService;

    private final SubCategoriaRepository subCategoriaRepository;

    public SubCategoriaResource(SubCategoriaService subCategoriaService, SubCategoriaRepository subCategoriaRepository) {
        this.subCategoriaService = subCategoriaService;
        this.subCategoriaRepository = subCategoriaRepository;
    }

    /**
     * {@code POST  /sub-categorias} : Create a new subCategoria.
     *
     * @param subCategoriaDTO the subCategoriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subCategoriaDTO, or with status {@code 400 (Bad Request)} if the subCategoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-categorias")
    public ResponseEntity<SubCategoriaDTO> createSubCategoria(@Valid @RequestBody SubCategoriaDTO subCategoriaDTO)
        throws URISyntaxException {
        log.debug("REST request to save SubCategoria : {}", subCategoriaDTO);
        if (subCategoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new subCategoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubCategoriaDTO result = subCategoriaService.save(subCategoriaDTO);
        return ResponseEntity
            .created(new URI("/api/sub-categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-categorias/:id} : Updates an existing subCategoria.
     *
     * @param id the id of the subCategoriaDTO to save.
     * @param subCategoriaDTO the subCategoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subCategoriaDTO,
     * or with status {@code 400 (Bad Request)} if the subCategoriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subCategoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-categorias/{id}")
    public ResponseEntity<SubCategoriaDTO> updateSubCategoria(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubCategoriaDTO subCategoriaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubCategoria : {}, {}", id, subCategoriaDTO);
        if (subCategoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subCategoriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subCategoriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubCategoriaDTO result = subCategoriaService.save(subCategoriaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-categorias/:id} : Partial updates given fields of an existing subCategoria, field will ignore if it is null
     *
     * @param id the id of the subCategoriaDTO to save.
     * @param subCategoriaDTO the subCategoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subCategoriaDTO,
     * or with status {@code 400 (Bad Request)} if the subCategoriaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subCategoriaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subCategoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-categorias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SubCategoriaDTO> partialUpdateSubCategoria(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubCategoriaDTO subCategoriaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubCategoria partially : {}, {}", id, subCategoriaDTO);
        if (subCategoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subCategoriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subCategoriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubCategoriaDTO> result = subCategoriaService.partialUpdate(subCategoriaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategoriaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-categorias} : get all the subCategorias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subCategorias in body.
     */
    @GetMapping("/sub-categorias")
    public ResponseEntity<List<SubCategoriaDTO>> getAllSubCategorias(Pageable pageable) {
        log.debug("REST request to get a page of SubCategorias");
        Page<SubCategoriaDTO> page = subCategoriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-categorias/:id} : get the "id" subCategoria.
     *
     * @param id the id of the subCategoriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subCategoriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-categorias/{id}")
    public ResponseEntity<SubCategoriaDTO> getSubCategoria(@PathVariable Long id) {
        log.debug("REST request to get SubCategoria : {}", id);
        Optional<SubCategoriaDTO> subCategoriaDTO = subCategoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subCategoriaDTO);
    }

    /**
     * {@code DELETE  /sub-categorias/:id} : delete the "id" subCategoria.
     *
     * @param id the id of the subCategoriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-categorias/{id}")
    public ResponseEntity<Void> deleteSubCategoria(@PathVariable Long id) {
        log.debug("REST request to delete SubCategoria : {}", id);
        subCategoriaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
