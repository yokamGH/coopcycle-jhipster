package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Restaurateur;
import com.mycompany.coopcycle.repository.RestaurateurRepository;
import com.mycompany.coopcycle.service.dto.RestaurateurDTO;
import com.mycompany.coopcycle.service.mapper.RestaurateurMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Restaurateur}.
 */
@Service
@Transactional
public class RestaurateurService {

    private final Logger log = LoggerFactory.getLogger(RestaurateurService.class);

    private final RestaurateurRepository restaurateurRepository;

    private final RestaurateurMapper restaurateurMapper;

    public RestaurateurService(RestaurateurRepository restaurateurRepository, RestaurateurMapper restaurateurMapper) {
        this.restaurateurRepository = restaurateurRepository;
        this.restaurateurMapper = restaurateurMapper;
    }

    /**
     * Save a restaurateur.
     *
     * @param restaurateurDTO the entity to save.
     * @return the persisted entity.
     */
    public RestaurateurDTO save(RestaurateurDTO restaurateurDTO) {
        log.debug("Request to save Restaurateur : {}", restaurateurDTO);
        Restaurateur restaurateur = restaurateurMapper.toEntity(restaurateurDTO);
        restaurateur = restaurateurRepository.save(restaurateur);
        return restaurateurMapper.toDto(restaurateur);
    }

    /**
     * Update a restaurateur.
     *
     * @param restaurateurDTO the entity to save.
     * @return the persisted entity.
     */
    public RestaurateurDTO update(RestaurateurDTO restaurateurDTO) {
        log.debug("Request to save Restaurateur : {}", restaurateurDTO);
        Restaurateur restaurateur = restaurateurMapper.toEntity(restaurateurDTO);
        restaurateur = restaurateurRepository.save(restaurateur);
        return restaurateurMapper.toDto(restaurateur);
    }

    /**
     * Partially update a restaurateur.
     *
     * @param restaurateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RestaurateurDTO> partialUpdate(RestaurateurDTO restaurateurDTO) {
        log.debug("Request to partially update Restaurateur : {}", restaurateurDTO);

        return restaurateurRepository
            .findById(restaurateurDTO.getId())
            .map(existingRestaurateur -> {
                restaurateurMapper.partialUpdate(existingRestaurateur, restaurateurDTO);

                return existingRestaurateur;
            })
            .map(restaurateurRepository::save)
            .map(restaurateurMapper::toDto);
    }

    /**
     * Get all the restaurateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RestaurateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Restaurateurs");
        return restaurateurRepository.findAll(pageable).map(restaurateurMapper::toDto);
    }

    /**
     * Get one restaurateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RestaurateurDTO> findOne(Long id) {
        log.debug("Request to get Restaurateur : {}", id);
        return restaurateurRepository.findById(id).map(restaurateurMapper::toDto);
    }

    /**
     * Delete the restaurateur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Restaurateur : {}", id);
        restaurateurRepository.deleteById(id);
    }
}
