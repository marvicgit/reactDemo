package mcdcoder.com.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import mcdcoder.com.IntegrationTest;
import mcdcoder.com.domain.CompraDetalle;
import mcdcoder.com.repository.CompraDetalleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CompraDetalleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompraDetalleResourceIT {

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    private static final Double DEFAULT_SUB_TOTAL = 1D;
    private static final Double UPDATED_SUB_TOTAL = 2D;

    private static final String ENTITY_API_URL = "/api/compra-detalles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompraDetalleRepository compraDetalleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompraDetalleMockMvc;

    private CompraDetalle compraDetalle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompraDetalle createEntity(EntityManager em) {
        CompraDetalle compraDetalle = new CompraDetalle().cantidad(DEFAULT_CANTIDAD).precio(DEFAULT_PRECIO).subTotal(DEFAULT_SUB_TOTAL);
        return compraDetalle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompraDetalle createUpdatedEntity(EntityManager em) {
        CompraDetalle compraDetalle = new CompraDetalle().cantidad(UPDATED_CANTIDAD).precio(UPDATED_PRECIO).subTotal(UPDATED_SUB_TOTAL);
        return compraDetalle;
    }

    @BeforeEach
    public void initTest() {
        compraDetalle = createEntity(em);
    }

    @Test
    @Transactional
    void createCompraDetalle() throws Exception {
        int databaseSizeBeforeCreate = compraDetalleRepository.findAll().size();
        // Create the CompraDetalle
        restCompraDetalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compraDetalle)))
            .andExpect(status().isCreated());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        CompraDetalle testCompraDetalle = compraDetalleList.get(compraDetalleList.size() - 1);
        assertThat(testCompraDetalle.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testCompraDetalle.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testCompraDetalle.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
    }

    @Test
    @Transactional
    void createCompraDetalleWithExistingId() throws Exception {
        // Create the CompraDetalle with an existing ID
        compraDetalle.setId(1L);

        int databaseSizeBeforeCreate = compraDetalleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraDetalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compraDetalle)))
            .andExpect(status().isBadRequest());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = compraDetalleRepository.findAll().size();
        // set the field null
        compraDetalle.setCantidad(null);

        // Create the CompraDetalle, which fails.

        restCompraDetalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compraDetalle)))
            .andExpect(status().isBadRequest());

        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompraDetalles() throws Exception {
        // Initialize the database
        compraDetalleRepository.saveAndFlush(compraDetalle);

        // Get all the compraDetalleList
        restCompraDetalleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compraDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    void getCompraDetalle() throws Exception {
        // Initialize the database
        compraDetalleRepository.saveAndFlush(compraDetalle);

        // Get the compraDetalle
        restCompraDetalleMockMvc
            .perform(get(ENTITY_API_URL_ID, compraDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compraDetalle.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingCompraDetalle() throws Exception {
        // Get the compraDetalle
        restCompraDetalleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompraDetalle() throws Exception {
        // Initialize the database
        compraDetalleRepository.saveAndFlush(compraDetalle);

        int databaseSizeBeforeUpdate = compraDetalleRepository.findAll().size();

        // Update the compraDetalle
        CompraDetalle updatedCompraDetalle = compraDetalleRepository.findById(compraDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedCompraDetalle are not directly saved in db
        em.detach(updatedCompraDetalle);
        updatedCompraDetalle.cantidad(UPDATED_CANTIDAD).precio(UPDATED_PRECIO).subTotal(UPDATED_SUB_TOTAL);

        restCompraDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompraDetalle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompraDetalle))
            )
            .andExpect(status().isOk());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeUpdate);
        CompraDetalle testCompraDetalle = compraDetalleList.get(compraDetalleList.size() - 1);
        assertThat(testCompraDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testCompraDetalle.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testCompraDetalle.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingCompraDetalle() throws Exception {
        int databaseSizeBeforeUpdate = compraDetalleRepository.findAll().size();
        compraDetalle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compraDetalle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compraDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompraDetalle() throws Exception {
        int databaseSizeBeforeUpdate = compraDetalleRepository.findAll().size();
        compraDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompraDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compraDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompraDetalle() throws Exception {
        int databaseSizeBeforeUpdate = compraDetalleRepository.findAll().size();
        compraDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompraDetalleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compraDetalle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompraDetalleWithPatch() throws Exception {
        // Initialize the database
        compraDetalleRepository.saveAndFlush(compraDetalle);

        int databaseSizeBeforeUpdate = compraDetalleRepository.findAll().size();

        // Update the compraDetalle using partial update
        CompraDetalle partialUpdatedCompraDetalle = new CompraDetalle();
        partialUpdatedCompraDetalle.setId(compraDetalle.getId());

        restCompraDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompraDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompraDetalle))
            )
            .andExpect(status().isOk());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeUpdate);
        CompraDetalle testCompraDetalle = compraDetalleList.get(compraDetalleList.size() - 1);
        assertThat(testCompraDetalle.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testCompraDetalle.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testCompraDetalle.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateCompraDetalleWithPatch() throws Exception {
        // Initialize the database
        compraDetalleRepository.saveAndFlush(compraDetalle);

        int databaseSizeBeforeUpdate = compraDetalleRepository.findAll().size();

        // Update the compraDetalle using partial update
        CompraDetalle partialUpdatedCompraDetalle = new CompraDetalle();
        partialUpdatedCompraDetalle.setId(compraDetalle.getId());

        partialUpdatedCompraDetalle.cantidad(UPDATED_CANTIDAD).precio(UPDATED_PRECIO).subTotal(UPDATED_SUB_TOTAL);

        restCompraDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompraDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompraDetalle))
            )
            .andExpect(status().isOk());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeUpdate);
        CompraDetalle testCompraDetalle = compraDetalleList.get(compraDetalleList.size() - 1);
        assertThat(testCompraDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testCompraDetalle.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testCompraDetalle.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingCompraDetalle() throws Exception {
        int databaseSizeBeforeUpdate = compraDetalleRepository.findAll().size();
        compraDetalle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, compraDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compraDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompraDetalle() throws Exception {
        int databaseSizeBeforeUpdate = compraDetalleRepository.findAll().size();
        compraDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompraDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compraDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompraDetalle() throws Exception {
        int databaseSizeBeforeUpdate = compraDetalleRepository.findAll().size();
        compraDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompraDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(compraDetalle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompraDetalle in the database
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompraDetalle() throws Exception {
        // Initialize the database
        compraDetalleRepository.saveAndFlush(compraDetalle);

        int databaseSizeBeforeDelete = compraDetalleRepository.findAll().size();

        // Delete the compraDetalle
        restCompraDetalleMockMvc
            .perform(delete(ENTITY_API_URL_ID, compraDetalle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompraDetalle> compraDetalleList = compraDetalleRepository.findAll();
        assertThat(compraDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
