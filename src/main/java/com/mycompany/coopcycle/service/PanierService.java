package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Panier;
import com.mycompany.coopcycle.repository.PanierRepository;
import com.mycompany.coopcycle.service.dto.PanierDTO;
import com.mycompany.coopcycle.service.mapper.PanierMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Panier}.
 */
@Service
@Transactional
public class PanierService {

    private final Logger log = LoggerFactory.getLogger(PanierService.class);

    private final PanierRepository panierRepository;

    private final PanierMapper panierMapper;

    public PanierService(PanierRepository panierRepository, PanierMapper panierMapper) {
        this.panierRepository = panierRepository;
        this.panierMapper = panierMapper;
    }

    /**
     * Save a panier.
     *
     * @param panierDTO the entity to save.
     * @return the persisted entity.
     */
    public PanierDTO save(PanierDTO panierDTO) {
        log.debug("Request to save Panier : {}", panierDTO);
        Panier panier = panierMapper.toEntity(panierDTO);
        panier = panierRepository.save(panier);
        return panierMapper.toDto(panier);
    }

    /**
     * Update a panier.
     *
     * @param panierDTO the entity to save.
     * @return the persisted entity.
     */
    public PanierDTO update(PanierDTO panierDTO) {
        log.debug("Request to save Panier : {}", panierDTO);
        Panier panier = panierMapper.toEntity(panierDTO);
        panier = panierRepository.save(panier);
        return panierMapper.toDto(panier);
    }

    /**
     * Partially update a panier.
     *
     * @param panierDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PanierDTO> partialUpdate(PanierDTO panierDTO) {
        log.debug("Request to partially update Panier : {}", panierDTO);

        return panierRepository
            .findById(panierDTO.getId())
            .map(existingPanier -> {
                panierMapper.partialUpdate(existingPanier, panierDTO);

                return existingPanier;
            })
            .map(panierRepository::save)
            .map(panierMapper::toDto);
    }

    /**
     * Get all the paniers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PanierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Paniers");
        return panierRepository.findAll(pageable).map(panierMapper::toDto);
    }

    /**
     * Get one panier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PanierDTO> findOne(Long id) {
        log.debug("Request to get Panier : {}", id);
        return panierRepository.findById(id).map(panierMapper::toDto);
    }

    /**
     * Delete the panier by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Panier : {}", id);
        panierRepository.deleteById(id);
    }
}
