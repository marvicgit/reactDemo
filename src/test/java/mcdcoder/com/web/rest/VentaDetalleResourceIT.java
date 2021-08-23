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
import mcdcoder.com.domain.VentaDetalle;
import mcdcoder.com.repository.VentaDetalleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VentaDetalleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VentaDetalleResourceIT {

    private static final String DEFAULT_TALLA = "AAAAAAAAAA";
    private static final String UPDATED_TALLA = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_URL_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMAGEN = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Double DEFAULT_SUB_TOTAL = 1D;
    private static final Double UPDATED_SUB_TOTAL = 2D;

    private static final String ENTITY_API_URL = "/api/venta-detalles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VentaDetalleRepository ventaDetalleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVentaDetalleMockMvc;

    private VentaDetalle ventaDetalle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VentaDetalle createEntity(EntityManager em) {
        VentaDetalle ventaDetalle = new VentaDetalle()
            .talla(DEFAULT_TALLA)
            .color(DEFAULT_COLOR)
            .urlImagen(DEFAULT_URL_IMAGEN)
            .precio(DEFAULT_PRECIO)
            .cantidad(DEFAULT_CANTIDAD)
            .subTotal(DEFAULT_SUB_TOTAL);
        return ventaDetalle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VentaDetalle createUpdatedEntity(EntityManager em) {
        VentaDetalle ventaDetalle = new VentaDetalle()
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .urlImagen(UPDATED_URL_IMAGEN)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .subTotal(UPDATED_SUB_TOTAL);
        return ventaDetalle;
    }

    @BeforeEach
    public void initTest() {
        ventaDetalle = createEntity(em);
    }

    @Test
    @Transactional
    void createVentaDetalle() throws Exception {
        int databaseSizeBeforeCreate = ventaDetalleRepository.findAll().size();
        // Create the VentaDetalle
        restVentaDetalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalle)))
            .andExpect(status().isCreated());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        VentaDetalle testVentaDetalle = ventaDetalleList.get(ventaDetalleList.size() - 1);
        assertThat(testVentaDetalle.getTalla()).isEqualTo(DEFAULT_TALLA);
        assertThat(testVentaDetalle.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVentaDetalle.getUrlImagen()).isEqualTo(DEFAULT_URL_IMAGEN);
        assertThat(testVentaDetalle.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testVentaDetalle.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testVentaDetalle.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
    }

    @Test
    @Transactional
    void createVentaDetalleWithExistingId() throws Exception {
        // Create the VentaDetalle with an existing ID
        ventaDetalle.setId(1L);

        int databaseSizeBeforeCreate = ventaDetalleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentaDetalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalle)))
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaDetalleRepository.findAll().size();
        // set the field null
        ventaDetalle.setCantidad(null);

        // Create the VentaDetalle, which fails.

        restVentaDetalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalle)))
            .andExpect(status().isBadRequest());

        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVentaDetalles() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        // Get all the ventaDetalleList
        restVentaDetalleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ventaDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].talla").value(hasItem(DEFAULT_TALLA)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].urlImagen").value(hasItem(DEFAULT_URL_IMAGEN)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    void getVentaDetalle() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        // Get the ventaDetalle
        restVentaDetalleMockMvc
            .perform(get(ENTITY_API_URL_ID, ventaDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ventaDetalle.getId().intValue()))
            .andExpect(jsonPath("$.talla").value(DEFAULT_TALLA))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.urlImagen").value(DEFAULT_URL_IMAGEN))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingVentaDetalle() throws Exception {
        // Get the ventaDetalle
        restVentaDetalleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVentaDetalle() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();

        // Update the ventaDetalle
        VentaDetalle updatedVentaDetalle = ventaDetalleRepository.findById(ventaDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedVentaDetalle are not directly saved in db
        em.detach(updatedVentaDetalle);
        updatedVentaDetalle
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .urlImagen(UPDATED_URL_IMAGEN)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .subTotal(UPDATED_SUB_TOTAL);

        restVentaDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVentaDetalle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVentaDetalle))
            )
            .andExpect(status().isOk());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
        VentaDetalle testVentaDetalle = ventaDetalleList.get(ventaDetalleList.size() - 1);
        assertThat(testVentaDetalle.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testVentaDetalle.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVentaDetalle.getUrlImagen()).isEqualTo(UPDATED_URL_IMAGEN);
        assertThat(testVentaDetalle.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testVentaDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testVentaDetalle.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ventaDetalle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaDetalle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVentaDetalleWithPatch() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();

        // Update the ventaDetalle using partial update
        VentaDetalle partialUpdatedVentaDetalle = new VentaDetalle();
        partialUpdatedVentaDetalle.setId(ventaDetalle.getId());

        partialUpdatedVentaDetalle.talla(UPDATED_TALLA).urlImagen(UPDATED_URL_IMAGEN).precio(UPDATED_PRECIO).cantidad(UPDATED_CANTIDAD);

        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVentaDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVentaDetalle))
            )
            .andExpect(status().isOk());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
        VentaDetalle testVentaDetalle = ventaDetalleList.get(ventaDetalleList.size() - 1);
        assertThat(testVentaDetalle.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testVentaDetalle.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVentaDetalle.getUrlImagen()).isEqualTo(UPDATED_URL_IMAGEN);
        assertThat(testVentaDetalle.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testVentaDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testVentaDetalle.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateVentaDetalleWithPatch() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();

        // Update the ventaDetalle using partial update
        VentaDetalle partialUpdatedVentaDetalle = new VentaDetalle();
        partialUpdatedVentaDetalle.setId(ventaDetalle.getId());

        partialUpdatedVentaDetalle
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .urlImagen(UPDATED_URL_IMAGEN)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .subTotal(UPDATED_SUB_TOTAL);

        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVentaDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVentaDetalle))
            )
            .andExpect(status().isOk());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
        VentaDetalle testVentaDetalle = ventaDetalleList.get(ventaDetalleList.size() - 1);
        assertThat(testVentaDetalle.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testVentaDetalle.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVentaDetalle.getUrlImagen()).isEqualTo(UPDATED_URL_IMAGEN);
        assertThat(testVentaDetalle.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testVentaDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testVentaDetalle.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ventaDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ventaDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVentaDetalle() throws Exception {
        int databaseSizeBeforeUpdate = ventaDetalleRepository.findAll().size();
        ventaDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ventaDetalle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VentaDetalle in the database
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVentaDetalle() throws Exception {
        // Initialize the database
        ventaDetalleRepository.saveAndFlush(ventaDetalle);

        int databaseSizeBeforeDelete = ventaDetalleRepository.findAll().size();

        // Delete the ventaDetalle
        restVentaDetalleMockMvc
            .perform(delete(ENTITY_API_URL_ID, ventaDetalle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VentaDetalle> ventaDetalleList = ventaDetalleRepository.findAll();
        assertThat(ventaDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
