package mcdcoder.com.service.mapper;

import mcdcoder.com.domain.*;
import mcdcoder.com.service.dto.VarianteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Variante} and its DTO {@link VarianteDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductoMapper.class, ImagenMapper.class })
public interface VarianteMapper extends EntityMapper<VarianteDTO, Variante> {
    @Mapping(target = "producto", source = "producto", qualifiedByName = "id")
    @Mapping(target = "imagen", source = "imagen", qualifiedByName = "id")
    VarianteDTO toDto(Variante s);
}
