package mcdcoder.com.service.mapper;

import mcdcoder.com.domain.*;
import mcdcoder.com.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = { SubCategoriaMapper.class })
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {
    @Mapping(target = "subCategoria", source = "subCategoria", qualifiedByName = "id")
    ProductoDTO toDto(Producto s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductoDTO toDtoId(Producto producto);
}
