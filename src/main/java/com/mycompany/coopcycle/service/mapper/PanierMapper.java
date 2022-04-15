package com.mycompany.coopcycle.service.mapper;

import com.mycompany.coopcycle.domain.Client;
import com.mycompany.coopcycle.domain.Livreur;
import com.mycompany.coopcycle.domain.Panier;
import com.mycompany.coopcycle.service.dto.ClientDTO;
import com.mycompany.coopcycle.service.dto.LivreurDTO;
import com.mycompany.coopcycle.service.dto.PanierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Panier} and its DTO {@link PanierDTO}.
 */
@Mapper(componentModel = "spring")
public interface PanierMapper extends EntityMapper<PanierDTO, Panier> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    @Mapping(target = "livreur", source = "livreur", qualifiedByName = "livreurId")
    PanierDTO toDto(Panier s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);

    @Named("livreurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LivreurDTO toDtoLivreurId(Livreur livreur);
}
