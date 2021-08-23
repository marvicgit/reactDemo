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
import mcdcoder.com.domain.Tarjeta;
import mcdcoder.com.repository.TarjetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TarjetaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TarjetaResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_VIGENCIA = "AAAAAAAAAA";
    private static final String UPDATED_VIGENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tarjetas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarjetaMockMvc;

    private Tarjeta tarjeta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarjeta createEntity(EntityManager em) {
        Tarjeta tarjeta = new Tarjeta().numero(DEFAULT_NUMERO).vigencia(DEFAULT_VIGENCIA).codigo(DEFAULT_CODIGO);
        return tarjeta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarjeta createUpdatedEntity(EntityManager em) {
        Tarjeta tarjeta = new Tarjeta().numero(UPDATED_NUMERO).vigencia(UPDATED_VIGENCIA).codigo(UPDATED_CODIGO);
        return tarjeta;
    }

    @BeforeEach
    public void initTest() {
        tarjeta = createEntity(em);
    }

    @Test
    @Transactional
    void createTarjeta() throws Exception {
        int databaseSizeBeforeCreate = tarjetaRepository.findAll().size();
        // Create the Tarjeta
        restTarjetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isCreated());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeCreate + 1);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTarjeta.getVigencia()).isEqualTo(DEFAULT_VIGENCIA);
        assertThat(testTarjeta.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    void createTarjetaWithExistingId() throws Exception {
        // Create the Tarjeta with an existing ID
        tarjeta.setId(1L);

        int databaseSizeBeforeCreate = tarjetaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarjetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isBadRequest());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setNumero(null);

        // Create the Tarjeta, which fails.

        restTarjetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVigenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setVigencia(null);

        // Create the Tarjeta, which fails.

        restTarjetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setCodigo(null);

        // Create the Tarjeta, which fails.

        restTarjetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTarjetas() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        // Get all the tarjetaList
        restTarjetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarjeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].vigencia").value(hasItem(DEFAULT_VIGENCIA)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }

    @Test
    @Transactional
    void getTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        // Get the tarjeta
        restTarjetaMockMvc
            .perform(get(ENTITY_API_URL_ID, tarjeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarjeta.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.vigencia").value(DEFAULT_VIGENCIA))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }

    @Test
    @Transactional
    void getNonExistingTarjeta() throws Exception {
        // Get the tarjeta
        restTarjetaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();

        // Update the tarjeta
        Tarjeta updatedTarjeta = tarjetaRepository.findById(tarjeta.getId()).get();
        // Disconnect from session so that the updates on updatedTarjeta are not directly saved in db
        em.detach(updatedTarjeta);
        updatedTarjeta.numero(UPDATED_NUMERO).vigencia(UPDATED_VIGENCIA).codigo(UPDATED_CODIGO);

        restTarjetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarjeta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTarjeta))
            )
            .andExpect(status().isOk());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTarjeta.getVigencia()).isEqualTo(UPDATED_VIGENCIA);
        assertThat(testTarjeta.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void putNonExistingTarjeta() throws Exception {
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();
        tarjeta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarjetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarjeta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tarjeta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarjeta() throws Exception {
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();
        tarjeta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarjetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tarjeta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarjeta() throws Exception {
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();
        tarjeta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarjetaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarjetaWithPatch() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();

        // Update the tarjeta using partial update
        Tarjeta partialUpdatedTarjeta = new Tarjeta();
        partialUpdatedTarjeta.setId(tarjeta.getId());

        partialUpdatedTarjeta.numero(UPDATED_NUMERO);

        restTarjetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarjeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarjeta))
            )
            .andExpect(status().isOk());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTarjeta.getVigencia()).isEqualTo(DEFAULT_VIGENCIA);
        assertThat(testTarjeta.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    void fullUpdateTarjetaWithPatch() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();

        // Update the tarjeta using partial update
        Tarjeta partialUpdatedTarjeta = new Tarjeta();
        partialUpdatedTarjeta.setId(tarjeta.getId());

        partialUpdatedTarjeta.numero(UPDATED_NUMERO).vigencia(UPDATED_VIGENCIA).codigo(UPDATED_CODIGO);

        restTarjetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarjeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarjeta))
            )
            .andExpect(status().isOk());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTarjeta.getVigencia()).isEqualTo(UPDATED_VIGENCIA);
        assertThat(testTarjeta.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void patchNonExistingTarjeta() throws Exception {
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();
        tarjeta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarjetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarjeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tarjeta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarjeta() throws Exception {
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();
        tarjeta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarjetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tarjeta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarjeta() throws Exception {
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();
        tarjeta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarjetaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        int databaseSizeBeforeDelete = tarjetaRepository.findAll().size();

        // Delete the tarjeta
        restTarjetaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarjeta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
