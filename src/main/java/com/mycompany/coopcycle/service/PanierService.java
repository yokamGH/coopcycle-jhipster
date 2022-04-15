package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Panier;
import com.mycompany.coopcycle.repository.PanierRepository;
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

    public PanierService(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }

    /**
     * Save a panier.
     *
     * @param panier the entity to save.
     * @return the persisted entity.
     */
    public Panier save(Panier panier) {
        log.debug("Request to save Panier : {}", panier);
        return panierRepository.save(panier);
    }

    /**
     * Update a panier.
     *
     * @param panier the entity to save.
     * @return the persisted entity.
     */
    public Panier update(Panier panier) {
        log.debug("Request to save Panier : {}", panier);
        return panierRepository.save(panier);
    }

    /**
     * Partially update a panier.
     *
     * @param panier the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Panier> partialUpdate(Panier panier) {
        log.debug("Request to partially update Panier : {}", panier);

        return panierRepository
            .findById(panier.getId())
            .map(existingPanier -> {
                if (panier.getDateCommande() != null) {
                    existingPanier.setDateCommande(panier.getDateCommande());
                }
                if (panier.getAdresseLivraison() != null) {
                    existingPanier.setAdresseLivraison(panier.getAdresseLivraison());
                }
                if (panier.getFraisService() != null) {
                    existingPanier.setFraisService(panier.getFraisService());
                }
                if (panier.getNetAPayer() != null) {
                    existingPanier.setNetAPayer(panier.getNetAPayer());
                }
                if (panier.getState() != null) {
                    existingPanier.setState(panier.getState());
                }
                if (panier.getDatePaiement() != null) {
                    existingPanier.setDatePaiement(panier.getDatePaiement());
                }
                if (panier.getMethodePaiement() != null) {
                    existingPanier.setMethodePaiement(panier.getMethodePaiement());
                }

                return existingPanier;
            })
            .map(panierRepository::save);
    }

    /**
     * Get all the paniers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Panier> findAll(Pageable pageable) {
        log.debug("Request to get all Paniers");
        return panierRepository.findAll(pageable);
    }

    /**
     * Get one panier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Panier> findOne(Long id) {
        log.debug("Request to get Panier : {}", id);
        return panierRepository.findById(id);
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
