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
import mcdcoder.com.domain.Variante;
import mcdcoder.com.repository.VarianteRepository;
import mcdcoder.com.service.dto.VarianteDTO;
import mcdcoder.com.service.mapper.VarianteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VarianteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VarianteResourceIT {

    private static final String DEFAULT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_TALLA = "AAAAAAAAAA";
    private static final String UPDATED_TALLA = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    private static final String ENTITY_API_URL = "/api/variantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VarianteRepository varianteRepository;

    @Autowired
    private VarianteMapper varianteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVarianteMockMvc;

    private Variante variante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Variante createEntity(EntityManager em) {
        Variante variante = new Variante()
            .sku(DEFAULT_SKU)
            .talla(DEFAULT_TALLA)
            .color(DEFAULT_COLOR)
            .stock(DEFAULT_STOCK)
            .precio(DEFAULT_PRECIO);
        return variante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Variante createUpdatedEntity(EntityManager em) {
        Variante variante = new Variante()
            .sku(UPDATED_SKU)
            .talla(UPDATED_TALLA)
            .color(UPDATED_COLOR)
            .stock(UPDATED_STOCK)
            .precio(UPDATED_PRECIO);
        return variante;
    }

    @BeforeEach
    public void initTest() {
        variante = createEntity(em);
    }

    @Test
    @Transactional
    void createVariante() throws Exception {
        int databaseSizeBeforeCreate = varianteRepository.findAll().size();
        // Create the Variante
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);
        restVarianteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isCreated());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeCreate + 1);
        Variante testVariante = varianteList.get(varianteList.size() - 1);
        assertThat(testVariante.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testVariante.getTalla()).isEqualTo(DEFAULT_TALLA);
        assertThat(testVariante.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVariante.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testVariante.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    void createVarianteWithExistingId() throws Exception {
        // Create the Variante with an existing ID
        variante.setId(1L);
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        int databaseSizeBeforeCreate = varianteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVarianteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTallaIsRequired() throws Exception {
        int databaseSizeBeforeTest = varianteRepository.findAll().size();
        // set the field null
        variante.setTalla(null);

        // Create the Variante, which fails.
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        restVarianteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = varianteRepository.findAll().size();
        // set the field null
        variante.setColor(null);

        // Create the Variante, which fails.
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        restVarianteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = varianteRepository.findAll().size();
        // set the field null
        variante.setStock(null);

        // Create the Variante, which fails.
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        restVarianteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVariantes() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        // Get all the varianteList
        restVarianteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variante.getId().intValue())))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU)))
            .andExpect(jsonPath("$.[*].talla").value(hasItem(DEFAULT_TALLA)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    void getVariante() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        // Get the variante
        restVarianteMockMvc
            .perform(get(ENTITY_API_URL_ID, variante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(variante.getId().intValue()))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU))
            .andExpect(jsonPath("$.talla").value(DEFAULT_TALLA))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingVariante() throws Exception {
        // Get the variante
        restVarianteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVariante() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();

        // Update the variante
        Variante updatedVariante = varianteRepository.findById(variante.getId()).get();
        // Disconnect from session so that the updates on updatedVariante are not directly saved in db
        em.detach(updatedVariante);
        updatedVariante.sku(UPDATED_SKU).talla(UPDATED_TALLA).color(UPDATED_COLOR).stock(UPDATED_STOCK).precio(UPDATED_PRECIO);
        VarianteDTO varianteDTO = varianteMapper.toDto(updatedVariante);

        restVarianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, varianteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(varianteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
        Variante testVariante = varianteList.get(varianteList.size() - 1);
        assertThat(testVariante.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testVariante.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testVariante.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVariante.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testVariante.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    void putNonExistingVariante() throws Exception {
        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();
        variante.setId(count.incrementAndGet());

        // Create the Variante
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVarianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, varianteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(varianteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVariante() throws Exception {
        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();
        variante.setId(count.incrementAndGet());

        // Create the Variante
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVarianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(varianteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVariante() throws Exception {
        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();
        variante.setId(count.incrementAndGet());

        // Create the Variante
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVarianteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVarianteWithPatch() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();

        // Update the variante using partial update
        Variante partialUpdatedVariante = new Variante();
        partialUpdatedVariante.setId(variante.getId());

        partialUpdatedVariante.sku(UPDATED_SKU).talla(UPDATED_TALLA).precio(UPDATED_PRECIO);

        restVarianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVariante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVariante))
            )
            .andExpect(status().isOk());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
        Variante testVariante = varianteList.get(varianteList.size() - 1);
        assertThat(testVariante.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testVariante.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testVariante.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVariante.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testVariante.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    void fullUpdateVarianteWithPatch() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();

        // Update the variante using partial update
        Variante partialUpdatedVariante = new Variante();
        partialUpdatedVariante.setId(variante.getId());

        partialUpdatedVariante.sku(UPDATED_SKU).talla(UPDATED_TALLA).color(UPDATED_COLOR).stock(UPDATED_STOCK).precio(UPDATED_PRECIO);

        restVarianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVariante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVariante))
            )
            .andExpect(status().isOk());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
        Variante testVariante = varianteList.get(varianteList.size() - 1);
        assertThat(testVariante.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testVariante.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testVariante.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVariante.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testVariante.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    void patchNonExistingVariante() throws Exception {
        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();
        variante.setId(count.incrementAndGet());

        // Create the Variante
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVarianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, varianteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(varianteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVariante() throws Exception {
        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();
        variante.setId(count.incrementAndGet());

        // Create the Variante
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVarianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(varianteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVariante() throws Exception {
        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();
        variante.setId(count.incrementAndGet());

        // Create the Variante
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVarianteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(varianteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVariante() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        int databaseSizeBeforeDelete = varianteRepository.findAll().size();

        // Delete the variante
        restVarianteMockMvc
            .perform(delete(ENTITY_API_URL_ID, variante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
