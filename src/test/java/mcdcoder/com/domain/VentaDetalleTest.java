package mcdcoder.com.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mcdcoder.com.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VentaDetalleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentaDetalle.class);
        VentaDetalle ventaDetalle1 = new VentaDetalle();
        ventaDetalle1.setId(1L);
        VentaDetalle ventaDetalle2 = new VentaDetalle();
        ventaDetalle2.setId(ventaDetalle1.getId());
        assertThat(ventaDetalle1).isEqualTo(ventaDetalle2);
        ventaDetalle2.setId(2L);
        assertThat(ventaDetalle1).isNotEqualTo(ventaDetalle2);
        ventaDetalle1.setId(null);
        assertThat(ventaDetalle1).isNotEqualTo(ventaDetalle2);
    }
}
