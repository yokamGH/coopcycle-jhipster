package com.mycompany.coopcycle.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuMapperTest {

    private MenuMapper menuMapper;

    @BeforeEach
    public void setUp() {
        menuMapper = new MenuMapperImpl();
    }
}
