package mcdcoder.com.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mcdcoder.com.domain.Tarjeta;
import mcdcoder.com.repository.TarjetaRepository;
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
 * REST controller for managing {@link mcdcoder.com.domain.Tarjeta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TarjetaResource {

    private final Logger log = LoggerFactory.getLogger(TarjetaResource.class);

    private static final String ENTITY_NAME = "tarjeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarjetaRepository tarjetaRepository;

    public TarjetaResource(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    /**
     * {@code POST  /tarjetas} : Create a new tarjeta.
     *
     * @param tarjeta the tarjeta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarjeta, or with status {@code 400 (Bad Request)} if the tarjeta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarjetas")
    public ResponseEntity<Tarjeta> createTarjeta(@Valid @RequestBody Tarjeta tarjeta) throws URISyntaxException {
        log.debug("REST request to save Tarjeta : {}", tarjeta);
        if (tarjeta.getId() != null) {
            throw new BadRequestAlertException("A new tarjeta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tarjeta result = tarjetaRepository.save(tarjeta);
        return ResponseEntity
            .created(new URI("/api/tarjetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarjetas/:id} : Updates an existing tarjeta.
     *
     * @param id the id of the tarjeta to save.
     * @param tarjeta the tarjeta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarjeta,
     * or with status {@code 400 (Bad Request)} if the tarjeta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarjeta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarjetas/{id}")
    public ResponseEntity<Tarjeta> updateTarjeta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Tarjeta tarjeta
    ) throws URISyntaxException {
        log.debug("REST request to update Tarjeta : {}, {}", id, tarjeta);
        if (tarjeta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarjeta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarjetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tarjeta result = tarjetaRepository.save(tarjeta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarjeta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tarjetas/:id} : Partial updates given fields of an existing tarjeta, field will ignore if it is null
     *
     * @param id the id of the tarjeta to save.
     * @param tarjeta the tarjeta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarjeta,
     * or with status {@code 400 (Bad Request)} if the tarjeta is not valid,
     * or with status {@code 404 (Not Found)} if the tarjeta is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarjeta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tarjetas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Tarjeta> partialUpdateTarjeta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tarjeta tarjeta
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tarjeta partially : {}, {}", id, tarjeta);
        if (tarjeta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarjeta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarjetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tarjeta> result = tarjetaRepository
            .findById(tarjeta.getId())
            .map(
                existingTarjeta -> {
                    if (tarjeta.getNumero() != null) {
                        existingTarjeta.setNumero(tarjeta.getNumero());
                    }
                    if (tarjeta.getVigencia() != null) {
                        existingTarjeta.setVigencia(tarjeta.getVigencia());
                    }
                    if (tarjeta.getCodigo() != null) {
                        existingTarjeta.setCodigo(tarjeta.getCodigo());
                    }

                    return existingTarjeta;
                }
            )
            .map(tarjetaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarjeta.getId().toString())
        );
    }

    /**
     * {@code GET  /tarjetas} : get all the tarjetas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarjetas in body.
     */
    @GetMapping("/tarjetas")
    public ResponseEntity<List<Tarjeta>> getAllTarjetas(Pageable pageable) {
        log.debug("REST request to get a page of Tarjetas");
        Page<Tarjeta> page = tarjetaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tarjetas/:id} : get the "id" tarjeta.
     *
     * @param id the id of the tarjeta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarjeta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarjetas/{id}")
    public ResponseEntity<Tarjeta> getTarjeta(@PathVariable Long id) {
        log.debug("REST request to get Tarjeta : {}", id);
        Optional<Tarjeta> tarjeta = tarjetaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tarjeta);
    }

    /**
     * {@code DELETE  /tarjetas/:id} : delete the "id" tarjeta.
     *
     * @param id the id of the tarjeta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarjetas/{id}")
    public ResponseEntity<Void> deleteTarjeta(@PathVariable Long id) {
        log.debug("REST request to delete Tarjeta : {}", id);
        tarjetaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
