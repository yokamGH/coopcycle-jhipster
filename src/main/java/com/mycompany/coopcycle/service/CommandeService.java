package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Commande;
import com.mycompany.coopcycle.repository.CommandeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Commande}.
 */
@Service
@Transactional
public class CommandeService {

    private final Logger log = LoggerFactory.getLogger(CommandeService.class);

    private final CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    /**
     * Save a commande.
     *
     * @param commande the entity to save.
     * @return the persisted entity.
     */
    public Commande save(Commande commande) {
        log.debug("Request to save Commande : {}", commande);
        return commandeRepository.save(commande);
    }

    /**
     * Update a commande.
     *
     * @param commande the entity to save.
     * @return the persisted entity.
     */
    public Commande update(Commande commande) {
        log.debug("Request to save Commande : {}", commande);
        return commandeRepository.save(commande);
    }

    /**
     * Partially update a commande.
     *
     * @param commande the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Commande> partialUpdate(Commande commande) {
        log.debug("Request to partially update Commande : {}", commande);

        return commandeRepository
            .findById(commande.getId())
            .map(existingCommande -> {
                if (commande.getQuantite() != null) {
                    existingCommande.setQuantite(commande.getQuantite());
                }
                if (commande.getTotal() != null) {
                    existingCommande.setTotal(commande.getTotal());
                }

                return existingCommande;
            })
            .map(commandeRepository::save);
    }

    /**
     * Get all the commandes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Commande> findAll() {
        log.debug("Request to get all Commandes");
        return commandeRepository.findAll();
    }

    /**
     * Get one commande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Commande> findOne(Long id) {
        log.debug("Request to get Commande : {}", id);
        return commandeRepository.findById(id);
    }

    /**
     * Delete the commande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Commande : {}", id);
        commandeRepository.deleteById(id);
    }
}
