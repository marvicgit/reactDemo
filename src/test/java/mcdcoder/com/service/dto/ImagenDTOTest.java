package mcdcoder.com.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mcdcoder.com.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImagenDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagenDTO.class);
        ImagenDTO imagenDTO1 = new ImagenDTO();
        imagenDTO1.setId(1L);
        ImagenDTO imagenDTO2 = new ImagenDTO();
        assertThat(imagenDTO1).isNotEqualTo(imagenDTO2);
        imagenDTO2.setId(imagenDTO1.getId());
        assertThat(imagenDTO1).isEqualTo(imagenDTO2);
        imagenDTO2.setId(2L);
        assertThat(imagenDTO1).isNotEqualTo(imagenDTO2);
        imagenDTO1.setId(null);
        assertThat(imagenDTO1).isNotEqualTo(imagenDTO2);
    }
}
