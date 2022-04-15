package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Restaurateur;
import com.mycompany.coopcycle.repository.RestaurateurRepository;
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

    public RestaurateurService(RestaurateurRepository restaurateurRepository) {
        this.restaurateurRepository = restaurateurRepository;
    }

    /**
     * Save a restaurateur.
     *
     * @param restaurateur the entity to save.
     * @return the persisted entity.
     */
    public Restaurateur save(Restaurateur restaurateur) {
        log.debug("Request to save Restaurateur : {}", restaurateur);
        return restaurateurRepository.save(restaurateur);
    }

    /**
     * Update a restaurateur.
     *
     * @param restaurateur the entity to save.
     * @return the persisted entity.
     */
    public Restaurateur update(Restaurateur restaurateur) {
        log.debug("Request to save Restaurateur : {}", restaurateur);
        return restaurateurRepository.save(restaurateur);
    }

    /**
     * Partially update a restaurateur.
     *
     * @param restaurateur the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Restaurateur> partialUpdate(Restaurateur restaurateur) {
        log.debug("Request to partially update Restaurateur : {}", restaurateur);

        return restaurateurRepository
            .findById(restaurateur.getId())
            .map(existingRestaurateur -> {
                if (restaurateur.getPrenom() != null) {
                    existingRestaurateur.setPrenom(restaurateur.getPrenom());
                }
                if (restaurateur.getNom() != null) {
                    existingRestaurateur.setNom(restaurateur.getNom());
                }
                if (restaurateur.getEmail() != null) {
                    existingRestaurateur.setEmail(restaurateur.getEmail());
                }
                if (restaurateur.getPhoneNumber() != null) {
                    existingRestaurateur.setPhoneNumber(restaurateur.getPhoneNumber());
                }
                if (restaurateur.getCommissions() != null) {
                    existingRestaurateur.setCommissions(restaurateur.getCommissions());
                }
                if (restaurateur.getEstDG() != null) {
                    existingRestaurateur.setEstDG(restaurateur.getEstDG());
                }
                if (restaurateur.getEstMenbreCA() != null) {
                    existingRestaurateur.setEstMenbreCA(restaurateur.getEstMenbreCA());
                }

                return existingRestaurateur;
            })
            .map(restaurateurRepository::save);
    }

    /**
     * Get all the restaurateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Restaurateur> findAll(Pageable pageable) {
        log.debug("Request to get all Restaurateurs");
        return restaurateurRepository.findAll(pageable);
    }

    /**
     * Get one restaurateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Restaurateur> findOne(Long id) {
        log.debug("Request to get Restaurateur : {}", id);
        return restaurateurRepository.findById(id);
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
