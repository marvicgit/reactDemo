package mcdcoder.com.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mcdcoder.com.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VarianteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VarianteDTO.class);
        VarianteDTO varianteDTO1 = new VarianteDTO();
        varianteDTO1.setId(1L);
        VarianteDTO varianteDTO2 = new VarianteDTO();
        assertThat(varianteDTO1).isNotEqualTo(varianteDTO2);
        varianteDTO2.setId(varianteDTO1.getId());
        assertThat(varianteDTO1).isEqualTo(varianteDTO2);
        varianteDTO2.setId(2L);
        assertThat(varianteDTO1).isNotEqualTo(varianteDTO2);
        varianteDTO1.setId(null);
        assertThat(varianteDTO1).isNotEqualTo(varianteDTO2);
    }
}
