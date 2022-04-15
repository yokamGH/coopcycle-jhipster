package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Menu;
import com.mycompany.coopcycle.repository.MenuRepository;
import com.mycompany.coopcycle.service.dto.MenuDTO;
import com.mycompany.coopcycle.service.mapper.MenuMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Menu}.
 */
@Service
@Transactional
public class MenuService {

    private final Logger log = LoggerFactory.getLogger(MenuService.class);

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    public MenuService(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    /**
     * Save a menu.
     *
     * @param menuDTO the entity to save.
     * @return the persisted entity.
     */
    public MenuDTO save(MenuDTO menuDTO) {
        log.debug("Request to save Menu : {}", menuDTO);
        Menu menu = menuMapper.toEntity(menuDTO);
        menu = menuRepository.save(menu);
        return menuMapper.toDto(menu);
    }

    /**
     * Update a menu.
     *
     * @param menuDTO the entity to save.
     * @return the persisted entity.
     */
    public MenuDTO update(MenuDTO menuDTO) {
        log.debug("Request to save Menu : {}", menuDTO);
        Menu menu = menuMapper.toEntity(menuDTO);
        menu = menuRepository.save(menu);
        return menuMapper.toDto(menu);
    }

    /**
     * Partially update a menu.
     *
     * @param menuDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MenuDTO> partialUpdate(MenuDTO menuDTO) {
        log.debug("Request to partially update Menu : {}", menuDTO);

        return menuRepository
            .findById(menuDTO.getId())
            .map(existingMenu -> {
                menuMapper.partialUpdate(existingMenu, menuDTO);

                return existingMenu;
            })
            .map(menuRepository::save)
            .map(menuMapper::toDto);
    }

    /**
     * Get all the menus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MenuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Menus");
        return menuRepository.findAll(pageable).map(menuMapper::toDto);
    }

    /**
     * Get one menu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MenuDTO> findOne(Long id) {
        log.debug("Request to get Menu : {}", id);
        return menuRepository.findById(id).map(menuMapper::toDto);
    }

    /**
     * Delete the menu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Menu : {}", id);
        menuRepository.deleteById(id);
    }
}
