package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Livreur;
import com.mycompany.coopcycle.repository.LivreurRepository;
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

    public LivreurService(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }

    /**
     * Save a livreur.
     *
     * @param livreur the entity to save.
     * @return the persisted entity.
     */
    public Livreur save(Livreur livreur) {
        log.debug("Request to save Livreur : {}", livreur);
        return livreurRepository.save(livreur);
    }

    /**
     * Update a livreur.
     *
     * @param livreur the entity to save.
     * @return the persisted entity.
     */
    public Livreur update(Livreur livreur) {
        log.debug("Request to save Livreur : {}", livreur);
        return livreurRepository.save(livreur);
    }

    /**
     * Partially update a livreur.
     *
     * @param livreur the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Livreur> partialUpdate(Livreur livreur) {
        log.debug("Request to partially update Livreur : {}", livreur);

        return livreurRepository
            .findById(livreur.getId())
            .map(existingLivreur -> {
                if (livreur.getPrenom() != null) {
                    existingLivreur.setPrenom(livreur.getPrenom());
                }
                if (livreur.getNom() != null) {
                    existingLivreur.setNom(livreur.getNom());
                }
                if (livreur.getEmail() != null) {
                    existingLivreur.setEmail(livreur.getEmail());
                }
                if (livreur.getPhoneNumber() != null) {
                    existingLivreur.setPhoneNumber(livreur.getPhoneNumber());
                }
                if (livreur.getCommissions() != null) {
                    existingLivreur.setCommissions(livreur.getCommissions());
                }
                if (livreur.getNbEtoiles() != null) {
                    existingLivreur.setNbEtoiles(livreur.getNbEtoiles());
                }
                if (livreur.getEstDG() != null) {
                    existingLivreur.setEstDG(livreur.getEstDG());
                }
                if (livreur.getEstMenbreCA() != null) {
                    existingLivreur.setEstMenbreCA(livreur.getEstMenbreCA());
                }

                return existingLivreur;
            })
            .map(livreurRepository::save);
    }

    /**
     * Get all the livreurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Livreur> findAll(Pageable pageable) {
        log.debug("Request to get all Livreurs");
        return livreurRepository.findAll(pageable);
    }

    /**
     * Get one livreur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Livreur> findOne(Long id) {
        log.debug("Request to get Livreur : {}", id);
        return livreurRepository.findById(id);
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
