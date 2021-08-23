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
import mcdcoder.com.domain.SubCategoria;
import mcdcoder.com.repository.SubCategoriaRepository;
import mcdcoder.com.service.dto.SubCategoriaDTO;
import mcdcoder.com.service.mapper.SubCategoriaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubCategoriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubCategoriaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-categorias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Autowired
    private SubCategoriaMapper subCategoriaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubCategoriaMockMvc;

    private SubCategoria subCategoria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategoria createEntity(EntityManager em) {
        SubCategoria subCategoria = new SubCategoria().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
        return subCategoria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategoria createUpdatedEntity(EntityManager em) {
        SubCategoria subCategoria = new SubCategoria().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        return subCategoria;
    }

    @BeforeEach
    public void initTest() {
        subCategoria = createEntity(em);
    }

    @Test
    @Transactional
    void createSubCategoria() throws Exception {
        int databaseSizeBeforeCreate = subCategoriaRepository.findAll().size();
        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);
        restSubCategoriaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeCreate + 1);
        SubCategoria testSubCategoria = subCategoriaList.get(subCategoriaList.size() - 1);
        assertThat(testSubCategoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSubCategoria.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createSubCategoriaWithExistingId() throws Exception {
        // Create the SubCategoria with an existing ID
        subCategoria.setId(1L);
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        int databaseSizeBeforeCreate = subCategoriaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubCategoriaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = subCategoriaRepository.findAll().size();
        // set the field null
        subCategoria.setNombre(null);

        // Create the SubCategoria, which fails.
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        restSubCategoriaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isBadRequest());

        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubCategorias() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get all the subCategoriaList
        restSubCategoriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCategoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getSubCategoria() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get the subCategoria
        restSubCategoriaMockMvc
            .perform(get(ENTITY_API_URL_ID, subCategoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subCategoria.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingSubCategoria() throws Exception {
        // Get the subCategoria
        restSubCategoriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubCategoria() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();

        // Update the subCategoria
        SubCategoria updatedSubCategoria = subCategoriaRepository.findById(subCategoria.getId()).get();
        // Disconnect from session so that the updates on updatedSubCategoria are not directly saved in db
        em.detach(updatedSubCategoria);
        updatedSubCategoria.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(updatedSubCategoria);

        restSubCategoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subCategoriaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
        SubCategoria testSubCategoria = subCategoriaList.get(subCategoriaList.size() - 1);
        assertThat(testSubCategoria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSubCategoria.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingSubCategoria() throws Exception {
        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();
        subCategoria.setId(count.incrementAndGet());

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubCategoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subCategoriaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubCategoria() throws Exception {
        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();
        subCategoria.setId(count.incrementAndGet());

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubCategoria() throws Exception {
        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();
        subCategoria.setId(count.incrementAndGet());

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoriaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubCategoriaWithPatch() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();

        // Update the subCategoria using partial update
        SubCategoria partialUpdatedSubCategoria = new SubCategoria();
        partialUpdatedSubCategoria.setId(subCategoria.getId());

        partialUpdatedSubCategoria.descripcion(UPDATED_DESCRIPCION);

        restSubCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubCategoria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategoria))
            )
            .andExpect(status().isOk());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
        SubCategoria testSubCategoria = subCategoriaList.get(subCategoriaList.size() - 1);
        assertThat(testSubCategoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSubCategoria.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateSubCategoriaWithPatch() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();

        // Update the subCategoria using partial update
        SubCategoria partialUpdatedSubCategoria = new SubCategoria();
        partialUpdatedSubCategoria.setId(subCategoria.getId());

        partialUpdatedSubCategoria.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restSubCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubCategoria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategoria))
            )
            .andExpect(status().isOk());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
        SubCategoria testSubCategoria = subCategoriaList.get(subCategoriaList.size() - 1);
        assertThat(testSubCategoria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSubCategoria.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingSubCategoria() throws Exception {
        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();
        subCategoria.setId(count.incrementAndGet());

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subCategoriaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubCategoria() throws Exception {
        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();
        subCategoria.setId(count.incrementAndGet());

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubCategoria() throws Exception {
        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();
        subCategoria.setId(count.incrementAndGet());

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubCategoria() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        int databaseSizeBeforeDelete = subCategoriaRepository.findAll().size();

        // Delete the subCategoria
        restSubCategoriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, subCategoria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
