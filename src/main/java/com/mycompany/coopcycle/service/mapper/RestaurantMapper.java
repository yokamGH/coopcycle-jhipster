package com.mycompany.coopcycle.service.mapper;

import com.mycompany.coopcycle.domain.Restaurant;
import com.mycompany.coopcycle.domain.Restaurateur;
import com.mycompany.coopcycle.service.dto.RestaurantDTO;
import com.mycompany.coopcycle.service.dto.RestaurateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restaurant} and its DTO {@link RestaurantDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {
    @Mapping(target = "restaurateur", source = "restaurateur", qualifiedByName = "restaurateurId")
    RestaurantDTO toDto(Restaurant s);

    @Named("restaurateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurateurDTO toDtoRestaurateurId(Restaurateur restaurateur);
}
