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
import mcdcoder.com.domain.Imagen;
import mcdcoder.com.repository.ImagenRepository;
import mcdcoder.com.service.dto.ImagenDTO;
import mcdcoder.com.service.mapper.ImagenMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ImagenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImagenResourceIT {

    private static final Integer DEFAULT_INDICE = 1;
    private static final Integer UPDATED_INDICE = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/imagens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ImagenRepository imagenRepository;

    @Autowired
    private ImagenMapper imagenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImagenMockMvc;

    private Imagen imagen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imagen createEntity(EntityManager em) {
        Imagen imagen = new Imagen()
            .indice(DEFAULT_INDICE)
            .nombre(DEFAULT_NOMBRE)
            .peso(DEFAULT_PESO)
            .extension(DEFAULT_EXTENSION)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        return imagen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imagen createUpdatedEntity(EntityManager em) {
        Imagen imagen = new Imagen()
            .indice(UPDATED_INDICE)
            .nombre(UPDATED_NOMBRE)
            .peso(UPDATED_PESO)
            .extension(UPDATED_EXTENSION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        return imagen;
    }

    @BeforeEach
    public void initTest() {
        imagen = createEntity(em);
    }

    @Test
    @Transactional
    void createImagen() throws Exception {
        int databaseSizeBeforeCreate = imagenRepository.findAll().size();
        // Create the Imagen
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);
        restImagenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isCreated());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeCreate + 1);
        Imagen testImagen = imagenList.get(imagenList.size() - 1);
        assertThat(testImagen.getIndice()).isEqualTo(DEFAULT_INDICE);
        assertThat(testImagen.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testImagen.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testImagen.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testImagen.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testImagen.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createImagenWithExistingId() throws Exception {
        // Create the Imagen with an existing ID
        imagen.setId(1L);
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        int databaseSizeBeforeCreate = imagenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIndiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagenRepository.findAll().size();
        // set the field null
        imagen.setIndice(null);

        // Create the Imagen, which fails.
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        restImagenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isBadRequest());

        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagenRepository.findAll().size();
        // set the field null
        imagen.setNombre(null);

        // Create the Imagen, which fails.
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        restImagenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isBadRequest());

        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagenRepository.findAll().size();
        // set the field null
        imagen.setPeso(null);

        // Create the Imagen, which fails.
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        restImagenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isBadRequest());

        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExtensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagenRepository.findAll().size();
        // set the field null
        imagen.setExtension(null);

        // Create the Imagen, which fails.
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        restImagenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isBadRequest());

        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllImagens() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        // Get all the imagenList
        restImagenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].indice").value(hasItem(DEFAULT_INDICE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }

    @Test
    @Transactional
    void getImagen() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        // Get the imagen
        restImagenMockMvc
            .perform(get(ENTITY_API_URL_ID, imagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imagen.getId().intValue()))
            .andExpect(jsonPath("$.indice").value(DEFAULT_INDICE))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    @Transactional
    void getNonExistingImagen() throws Exception {
        // Get the imagen
        restImagenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewImagen() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();

        // Update the imagen
        Imagen updatedImagen = imagenRepository.findById(imagen.getId()).get();
        // Disconnect from session so that the updates on updatedImagen are not directly saved in db
        em.detach(updatedImagen);
        updatedImagen
            .indice(UPDATED_INDICE)
            .nombre(UPDATED_NOMBRE)
            .peso(UPDATED_PESO)
            .extension(UPDATED_EXTENSION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        ImagenDTO imagenDTO = imagenMapper.toDto(updatedImagen);

        restImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imagenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagenDTO))
            )
            .andExpect(status().isOk());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
        Imagen testImagen = imagenList.get(imagenList.size() - 1);
        assertThat(testImagen.getIndice()).isEqualTo(UPDATED_INDICE);
        assertThat(testImagen.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testImagen.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testImagen.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testImagen.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testImagen.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingImagen() throws Exception {
        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();
        imagen.setId(count.incrementAndGet());

        // Create the Imagen
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imagenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImagen() throws Exception {
        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();
        imagen.setId(count.incrementAndGet());

        // Create the Imagen
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImagen() throws Exception {
        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();
        imagen.setId(count.incrementAndGet());

        // Create the Imagen
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImagenWithPatch() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();

        // Update the imagen using partial update
        Imagen partialUpdatedImagen = new Imagen();
        partialUpdatedImagen.setId(imagen.getId());

        partialUpdatedImagen
            .indice(UPDATED_INDICE)
            .nombre(UPDATED_NOMBRE)
            .peso(UPDATED_PESO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);

        restImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImagen))
            )
            .andExpect(status().isOk());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
        Imagen testImagen = imagenList.get(imagenList.size() - 1);
        assertThat(testImagen.getIndice()).isEqualTo(UPDATED_INDICE);
        assertThat(testImagen.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testImagen.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testImagen.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testImagen.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testImagen.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateImagenWithPatch() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();

        // Update the imagen using partial update
        Imagen partialUpdatedImagen = new Imagen();
        partialUpdatedImagen.setId(imagen.getId());

        partialUpdatedImagen
            .indice(UPDATED_INDICE)
            .nombre(UPDATED_NOMBRE)
            .peso(UPDATED_PESO)
            .extension(UPDATED_EXTENSION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);

        restImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImagen))
            )
            .andExpect(status().isOk());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
        Imagen testImagen = imagenList.get(imagenList.size() - 1);
        assertThat(testImagen.getIndice()).isEqualTo(UPDATED_INDICE);
        assertThat(testImagen.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testImagen.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testImagen.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testImagen.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testImagen.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingImagen() throws Exception {
        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();
        imagen.setId(count.incrementAndGet());

        // Create the Imagen
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, imagenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImagen() throws Exception {
        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();
        imagen.setId(count.incrementAndGet());

        // Create the Imagen
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImagen() throws Exception {
        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();
        imagen.setId(count.incrementAndGet());

        // Create the Imagen
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(imagenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImagen() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        int databaseSizeBeforeDelete = imagenRepository.findAll().size();

        // Delete the imagen
        restImagenMockMvc
            .perform(delete(ENTITY_API_URL_ID, imagen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
