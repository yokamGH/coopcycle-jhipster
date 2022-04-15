package com.mycompany.coopcycle.service.mapper;

import com.mycompany.coopcycle.domain.Cooperative;
import com.mycompany.coopcycle.domain.Restaurateur;
import com.mycompany.coopcycle.service.dto.CooperativeDTO;
import com.mycompany.coopcycle.service.dto.RestaurateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restaurateur} and its DTO {@link RestaurateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestaurateurMapper extends EntityMapper<RestaurateurDTO, Restaurateur> {
    @Mapping(target = "cooperative", source = "cooperative", qualifiedByName = "cooperativeId")
    RestaurateurDTO toDto(Restaurateur s);

    @Named("cooperativeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CooperativeDTO toDtoCooperativeId(Cooperative cooperative);
}
