package com.mycompany.coopcycle.service.mapper;

import com.mycompany.coopcycle.domain.Cooperative;
import com.mycompany.coopcycle.service.dto.CooperativeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cooperative} and its DTO {@link CooperativeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CooperativeMapper extends EntityMapper<CooperativeDTO, Cooperative> {}
