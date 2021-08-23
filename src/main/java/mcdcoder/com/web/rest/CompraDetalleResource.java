package mcdcoder.com.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mcdcoder.com.domain.CompraDetalle;
import mcdcoder.com.repository.CompraDetalleRepository;
import mcdcoder.com.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mcdcoder.com.domain.CompraDetalle}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CompraDetalleResource {

    private final Logger log = LoggerFactory.getLogger(CompraDetalleResource.class);

    private static final String ENTITY_NAME = "compraDetalle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompraDetalleRepository compraDetalleRepository;

    public CompraDetalleResource(CompraDetalleRepository compraDetalleRepository) {
        this.compraDetalleRepository = compraDetalleRepository;
    }

    /**
     * {@code POST  /compra-detalles} : Create a new compraDetalle.
     *
     * @param compraDetalle the compraDetalle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compraDetalle, or with status {@code 400 (Bad Request)} if the compraDetalle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compra-detalles")
    public ResponseEntity<CompraDetalle> createCompraDetalle(@Valid @RequestBody CompraDetalle compraDetalle) throws URISyntaxException {
        log.debug("REST request to save CompraDetalle : {}", compraDetalle);
        if (compraDetalle.getId() != null) {
            throw new BadRequestAlertException("A new compraDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompraDetalle result = compraDetalleRepository.save(compraDetalle);
        return ResponseEntity
            .created(new URI("/api/compra-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compra-detalles/:id} : Updates an existing compraDetalle.
     *
     * @param id the id of the compraDetalle to save.
     * @param compraDetalle the compraDetalle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compraDetalle,
     * or with status {@code 400 (Bad Request)} if the compraDetalle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compraDetalle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compra-detalles/{id}")
    public ResponseEntity<CompraDetalle> updateCompraDetalle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompraDetalle compraDetalle
    ) throws URISyntaxException {
        log.debug("REST request to update CompraDetalle : {}, {}", id, compraDetalle);
        if (compraDetalle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compraDetalle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compraDetalleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompraDetalle result = compraDetalleRepository.save(compraDetalle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compraDetalle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /compra-detalles/:id} : Partial updates given fields of an existing compraDetalle, field will ignore if it is null
     *
     * @param id the id of the compraDetalle to save.
     * @param compraDetalle the compraDetalle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compraDetalle,
     * or with status {@code 400 (Bad Request)} if the compraDetalle is not valid,
     * or with status {@code 404 (Not Found)} if the compraDetalle is not found,
     * or with status {@code 500 (Internal Server Error)} if the compraDetalle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compra-detalles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CompraDetalle> partialUpdateCompraDetalle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompraDetalle compraDetalle
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompraDetalle partially : {}, {}", id, compraDetalle);
        if (compraDetalle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compraDetalle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compraDetalleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompraDetalle> result = compraDetalleRepository
            .findById(compraDetalle.getId())
            .map(
                existingCompraDetalle -> {
                    if (compraDetalle.getCantidad() != null) {
                        existingCompraDetalle.setCantidad(compraDetalle.getCantidad());
                    }
                    if (compraDetalle.getPrecio() != null) {
                        existingCompraDetalle.setPrecio(compraDetalle.getPrecio());
                    }
                    if (compraDetalle.getSubTotal() != null) {
                        existingCompraDetalle.setSubTotal(compraDetalle.getSubTotal());
                    }

                    return existingCompraDetalle;
                }
            )
            .map(compraDetalleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compraDetalle.getId().toString())
        );
    }

    /**
     * {@code GET  /compra-detalles} : get all the compraDetalles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compraDetalles in body.
     */
    @GetMapping("/compra-detalles")
    public ResponseEntity<List<CompraDetalle>> getAllCompraDetalles(Pageable pageable) {
        log.debug("REST request to get a page of CompraDetalles");
        Page<CompraDetalle> page = compraDetalleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compra-detalles/:id} : get the "id" compraDetalle.
     *
     * @param id the id of the compraDetalle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compraDetalle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compra-detalles/{id}")
    public ResponseEntity<CompraDetalle> getCompraDetalle(@PathVariable Long id) {
        log.debug("REST request to get CompraDetalle : {}", id);
        Optional<CompraDetalle> compraDetalle = compraDetalleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(compraDetalle);
    }

    /**
     * {@code DELETE  /compra-detalles/:id} : delete the "id" compraDetalle.
     *
     * @param id the id of the compraDetalle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compra-detalles/{id}")
    public ResponseEntity<Void> deleteCompraDetalle(@PathVariable Long id) {
        log.debug("REST request to delete CompraDetalle : {}", id);
        compraDetalleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
