package mcdcoder.com.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VarianteMapperTest {

    private VarianteMapper varianteMapper;

    @BeforeEach
    public void setUp() {
        varianteMapper = new VarianteMapperImpl();
    }
}
