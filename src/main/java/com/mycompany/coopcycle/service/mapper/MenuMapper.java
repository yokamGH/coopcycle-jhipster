package com.mycompany.coopcycle.service.mapper;

import com.mycompany.coopcycle.domain.Menu;
import com.mycompany.coopcycle.domain.Restaurant;
import com.mycompany.coopcycle.service.dto.MenuDTO;
import com.mycompany.coopcycle.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menu} and its DTO {@link MenuDTO}.
 */
@Mapper(componentModel = "spring")
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantId")
    MenuDTO toDto(Menu s);

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);
}
