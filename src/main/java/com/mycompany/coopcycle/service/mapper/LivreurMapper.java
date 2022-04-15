package com.mycompany.coopcycle.service.mapper;

import com.mycompany.coopcycle.domain.Cooperative;
import com.mycompany.coopcycle.domain.Livreur;
import com.mycompany.coopcycle.service.dto.CooperativeDTO;
import com.mycompany.coopcycle.service.dto.LivreurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Livreur} and its DTO {@link LivreurDTO}.
 */
@Mapper(componentModel = "spring")
public interface LivreurMapper extends EntityMapper<LivreurDTO, Livreur> {
    @Mapping(target = "cooperative", source = "cooperative", qualifiedByName = "cooperativeId")
    LivreurDTO toDto(Livreur s);

    @Named("cooperativeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CooperativeDTO toDtoCooperativeId(Cooperative cooperative);
}
