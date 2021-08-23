package mcdcoder.com.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mcdcoder.com.domain.VentaDetalle;
import mcdcoder.com.repository.VentaDetalleRepository;
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
 * REST controller for managing {@link mcdcoder.com.domain.VentaDetalle}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VentaDetalleResource {

    private final Logger log = LoggerFactory.getLogger(VentaDetalleResource.class);

    private static final String ENTITY_NAME = "ventaDetalle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VentaDetalleRepository ventaDetalleRepository;

    public VentaDetalleResource(VentaDetalleRepository ventaDetalleRepository) {
        this.ventaDetalleRepository = ventaDetalleRepository;
    }

    /**
     * {@code POST  /venta-detalles} : Create a new ventaDetalle.
     *
     * @param ventaDetalle the ventaDetalle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ventaDetalle, or with status {@code 400 (Bad Request)} if the ventaDetalle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/venta-detalles")
    public ResponseEntity<VentaDetalle> createVentaDetalle(@Valid @RequestBody VentaDetalle ventaDetalle) throws URISyntaxException {
        log.debug("REST request to save VentaDetalle : {}", ventaDetalle);
        if (ventaDetalle.getId() != null) {
            throw new BadRequestAlertException("A new ventaDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VentaDetalle result = ventaDetalleRepository.save(ventaDetalle);
        return ResponseEntity
            .created(new URI("/api/venta-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /venta-detalles/:id} : Updates an existing ventaDetalle.
     *
     * @param id the id of the ventaDetalle to save.
     * @param ventaDetalle the ventaDetalle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventaDetalle,
     * or with status {@code 400 (Bad Request)} if the ventaDetalle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ventaDetalle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/venta-detalles/{id}")
    public ResponseEntity<VentaDetalle> updateVentaDetalle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VentaDetalle ventaDetalle
    ) throws URISyntaxException {
        log.debug("REST request to update VentaDetalle : {}, {}", id, ventaDetalle);
        if (ventaDetalle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ventaDetalle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaDetalleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VentaDetalle result = ventaDetalleRepository.save(ventaDetalle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventaDetalle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /venta-detalles/:id} : Partial updates given fields of an existing ventaDetalle, field will ignore if it is null
     *
     * @param id the id of the ventaDetalle to save.
     * @param ventaDetalle the ventaDetalle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventaDetalle,
     * or with status {@code 400 (Bad Request)} if the ventaDetalle is not valid,
     * or with status {@code 404 (Not Found)} if the ventaDetalle is not found,
     * or with status {@code 500 (Internal Server Error)} if the ventaDetalle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/venta-detalles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VentaDetalle> partialUpdateVentaDetalle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VentaDetalle ventaDetalle
    ) throws URISyntaxException {
        log.debug("REST request to partial update VentaDetalle partially : {}, {}", id, ventaDetalle);
        if (ventaDetalle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ventaDetalle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaDetalleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VentaDetalle> result = ventaDetalleRepository
            .findById(ventaDetalle.getId())
            .map(
                existingVentaDetalle -> {
                    if (ventaDetalle.getTalla() != null) {
                        existingVentaDetalle.setTalla(ventaDetalle.getTalla());
                    }
                    if (ventaDetalle.getColor() != null) {
                        existingVentaDetalle.setColor(ventaDetalle.getColor());
                    }
                    if (ventaDetalle.getUrlImagen() != null) {
                        existingVentaDetalle.setUrlImagen(ventaDetalle.getUrlImagen());
                    }
                    if (ventaDetalle.getPrecio() != null) {
                        existingVentaDetalle.setPrecio(ventaDetalle.getPrecio());
                    }
                    if (ventaDetalle.getCantidad() != null) {
                        existingVentaDetalle.setCantidad(ventaDetalle.getCantidad());
                    }
                    if (ventaDetalle.getSubTotal() != null) {
                        existingVentaDetalle.setSubTotal(ventaDetalle.getSubTotal());
                    }

                    return existingVentaDetalle;
                }
            )
            .map(ventaDetalleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventaDetalle.getId().toString())
        );
    }

    /**
     * {@code GET  /venta-detalles} : get all the ventaDetalles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventaDetalles in body.
     */
    @GetMapping("/venta-detalles")
    public ResponseEntity<List<VentaDetalle>> getAllVentaDetalles(Pageable pageable) {
        log.debug("REST request to get a page of VentaDetalles");
        Page<VentaDetalle> page = ventaDetalleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /venta-detalles/:id} : get the "id" ventaDetalle.
     *
     * @param id the id of the ventaDetalle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ventaDetalle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/venta-detalles/{id}")
    public ResponseEntity<VentaDetalle> getVentaDetalle(@PathVariable Long id) {
        log.debug("REST request to get VentaDetalle : {}", id);
        Optional<VentaDetalle> ventaDetalle = ventaDetalleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ventaDetalle);
    }

    /**
     * {@code DELETE  /venta-detalles/:id} : delete the "id" ventaDetalle.
     *
     * @param id the id of the ventaDetalle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/venta-detalles/{id}")
    public ResponseEntity<Void> deleteVentaDetalle(@PathVariable Long id) {
        log.debug("REST request to delete VentaDetalle : {}", id);
        ventaDetalleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
