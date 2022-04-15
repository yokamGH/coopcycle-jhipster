package com.mycompany.coopcycle.service.mapper;

import com.mycompany.coopcycle.domain.Client;
import com.mycompany.coopcycle.domain.Cooperative;
import com.mycompany.coopcycle.service.dto.ClientDTO;
import com.mycompany.coopcycle.service.dto.CooperativeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "cooperative", source = "cooperative", qualifiedByName = "cooperativeId")
    ClientDTO toDto(Client s);

    @Named("cooperativeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CooperativeDTO toDtoCooperativeId(Cooperative cooperative);
}
