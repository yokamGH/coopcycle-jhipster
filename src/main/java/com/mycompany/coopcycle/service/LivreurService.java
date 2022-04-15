package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Livreur;
import com.mycompany.coopcycle.repository.LivreurRepository;
import com.mycompany.coopcycle.service.dto.LivreurDTO;
import com.mycompany.coopcycle.service.mapper.LivreurMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Livreur}.
 */
@Service
@Transactional
public class LivreurService {

    private final Logger log = LoggerFactory.getLogger(LivreurService.class);

    private final LivreurRepository livreurRepository;

    private final LivreurMapper livreurMapper;

    public LivreurService(LivreurRepository livreurRepository, LivreurMapper livreurMapper) {
        this.livreurRepository = livreurRepository;
        this.livreurMapper = livreurMapper;
    }

    /**
     * Save a livreur.
     *
     * @param livreurDTO the entity to save.
     * @return the persisted entity.
     */
    public LivreurDTO save(LivreurDTO livreurDTO) {
        log.debug("Request to save Livreur : {}", livreurDTO);
        Livreur livreur = livreurMapper.toEntity(livreurDTO);
        livreur = livreurRepository.save(livreur);
        return livreurMapper.toDto(livreur);
    }

    /**
     * Update a livreur.
     *
     * @param livreurDTO the entity to save.
     * @return the persisted entity.
     */
    public LivreurDTO update(LivreurDTO livreurDTO) {
        log.debug("Request to save Livreur : {}", livreurDTO);
        Livreur livreur = livreurMapper.toEntity(livreurDTO);
        livreur = livreurRepository.save(livreur);
        return livreurMapper.toDto(livreur);
    }

    /**
     * Partially update a livreur.
     *
     * @param livreurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LivreurDTO> partialUpdate(LivreurDTO livreurDTO) {
        log.debug("Request to partially update Livreur : {}", livreurDTO);

        return livreurRepository
            .findById(livreurDTO.getId())
            .map(existingLivreur -> {
                livreurMapper.partialUpdate(existingLivreur, livreurDTO);

                return existingLivreur;
            })
            .map(livreurRepository::save)
            .map(livreurMapper::toDto);
    }

    /**
     * Get all the livreurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LivreurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Livreurs");
        return livreurRepository.findAll(pageable).map(livreurMapper::toDto);
    }

    /**
     * Get one livreur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LivreurDTO> findOne(Long id) {
        log.debug("Request to get Livreur : {}", id);
        return livreurRepository.findById(id).map(livreurMapper::toDto);
    }

    /**
     * Delete the livreur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Livreur : {}", id);
        livreurRepository.deleteById(id);
    }
}
