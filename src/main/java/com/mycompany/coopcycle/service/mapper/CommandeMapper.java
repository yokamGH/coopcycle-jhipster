package com.mycompany.coopcycle.service.mapper;

import com.mycompany.coopcycle.domain.Commande;
import com.mycompany.coopcycle.domain.Menu;
import com.mycompany.coopcycle.domain.Panier;
import com.mycompany.coopcycle.service.dto.CommandeDTO;
import com.mycompany.coopcycle.service.dto.MenuDTO;
import com.mycompany.coopcycle.service.dto.PanierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commande} and its DTO {@link CommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {
    @Mapping(target = "panier", source = "panier", qualifiedByName = "panierId")
    @Mapping(target = "menu", source = "menu", qualifiedByName = "menuId")
    CommandeDTO toDto(Commande s);

    @Named("panierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PanierDTO toDtoPanierId(Panier panier);

    @Named("menuId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MenuDTO toDtoMenuId(Menu menu);
}
