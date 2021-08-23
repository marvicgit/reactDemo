package mcdcoder.com.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mcdcoder.com.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompraDetalleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraDetalle.class);
        CompraDetalle compraDetalle1 = new CompraDetalle();
        compraDetalle1.setId(1L);
        CompraDetalle compraDetalle2 = new CompraDetalle();
        compraDetalle2.setId(compraDetalle1.getId());
        assertThat(compraDetalle1).isEqualTo(compraDetalle2);
        compraDetalle2.setId(2L);
        assertThat(compraDetalle1).isNotEqualTo(compraDetalle2);
        compraDetalle1.setId(null);
        assertThat(compraDetalle1).isNotEqualTo(compraDetalle2);
    }
}
