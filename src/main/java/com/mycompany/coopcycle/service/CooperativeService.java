package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Cooperative;
import com.mycompany.coopcycle.repository.CooperativeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cooperative}.
 */
@Service
@Transactional
public class CooperativeService {

    private final Logger log = LoggerFactory.getLogger(CooperativeService.class);

    private final CooperativeRepository cooperativeRepository;

    public CooperativeService(CooperativeRepository cooperativeRepository) {
        this.cooperativeRepository = cooperativeRepository;
    }

    /**
     * Save a cooperative.
     *
     * @param cooperative the entity to save.
     * @return the persisted entity.
     */
    public Cooperative save(Cooperative cooperative) {
        log.debug("Request to save Cooperative : {}", cooperative);
        return cooperativeRepository.save(cooperative);
    }

    /**
     * Update a cooperative.
     *
     * @param cooperative the entity to save.
     * @return the persisted entity.
     */
    public Cooperative update(Cooperative cooperative) {
        log.debug("Request to save Cooperative : {}", cooperative);
        return cooperativeRepository.save(cooperative);
    }

    /**
     * Partially update a cooperative.
     *
     * @param cooperative the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Cooperative> partialUpdate(Cooperative cooperative) {
        log.debug("Request to partially update Cooperative : {}", cooperative);

        return cooperativeRepository
            .findById(cooperative.getId())
            .map(existingCooperative -> {
                if (cooperative.getNom() != null) {
                    existingCooperative.setNom(cooperative.getNom());
                }
                if (cooperative.getLocalisation() != null) {
                    existingCooperative.setLocalisation(cooperative.getLocalisation());
                }
                if (cooperative.getNbAdherents() != null) {
                    existingCooperative.setNbAdherents(cooperative.getNbAdherents());
                }

                return existingCooperative;
            })
            .map(cooperativeRepository::save);
    }

    /**
     * Get all the cooperatives.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Cooperative> findAll() {
        log.debug("Request to get all Cooperatives");
        return cooperativeRepository.findAll();
    }

    /**
     * Get one cooperative by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cooperative> findOne(Long id) {
        log.debug("Request to get Cooperative : {}", id);
        return cooperativeRepository.findById(id);
    }

    /**
     * Delete the cooperative by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cooperative : {}", id);
        cooperativeRepository.deleteById(id);
    }
}
