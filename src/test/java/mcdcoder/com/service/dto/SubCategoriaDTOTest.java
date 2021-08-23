package mcdcoder.com.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mcdcoder.com.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubCategoriaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCategoriaDTO.class);
        SubCategoriaDTO subCategoriaDTO1 = new SubCategoriaDTO();
        subCategoriaDTO1.setId(1L);
        SubCategoriaDTO subCategoriaDTO2 = new SubCategoriaDTO();
        assertThat(subCategoriaDTO1).isNotEqualTo(subCategoriaDTO2);
        subCategoriaDTO2.setId(subCategoriaDTO1.getId());
        assertThat(subCategoriaDTO1).isEqualTo(subCategoriaDTO2);
        subCategoriaDTO2.setId(2L);
        assertThat(subCategoriaDTO1).isNotEqualTo(subCategoriaDTO2);
        subCategoriaDTO1.setId(null);
        assertThat(subCategoriaDTO1).isNotEqualTo(subCategoriaDTO2);
    }
}
