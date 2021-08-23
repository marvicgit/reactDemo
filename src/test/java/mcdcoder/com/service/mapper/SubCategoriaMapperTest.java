package mcdcoder.com.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubCategoriaMapperTest {

    private SubCategoriaMapper subCategoriaMapper;

    @BeforeEach
    public void setUp() {
        subCategoriaMapper = new SubCategoriaMapperImpl();
    }
}
