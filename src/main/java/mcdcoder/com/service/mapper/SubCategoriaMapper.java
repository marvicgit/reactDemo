package mcdcoder.com.service.mapper;

import mcdcoder.com.domain.*;
import mcdcoder.com.service.dto.SubCategoriaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubCategoria} and its DTO {@link SubCategoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategoriaMapper.class })
public interface SubCategoriaMapper extends EntityMapper<SubCategoriaDTO, SubCategoria> {
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "id")
    SubCategoriaDTO toDto(SubCategoria s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubCategoriaDTO toDtoId(SubCategoria subCategoria);
}
