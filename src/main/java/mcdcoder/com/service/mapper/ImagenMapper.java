package mcdcoder.com.service.mapper;

import mcdcoder.com.domain.*;
import mcdcoder.com.service.dto.ImagenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Imagen} and its DTO {@link ImagenDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductoMapper.class })
public interface ImagenMapper extends EntityMapper<ImagenDTO, Imagen> {
    @Mapping(target = "producto", source = "producto", qualifiedByName = "id")
    ImagenDTO toDto(Imagen s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ImagenDTO toDtoId(Imagen imagen);
}
