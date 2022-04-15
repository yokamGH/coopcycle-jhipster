package com.mycompany.coopcycle.service;

import com.mycompany.coopcycle.domain.Restaurant;
import com.mycompany.coopcycle.repository.RestaurantRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Restaurant}.
 */
@Service
@Transactional
public class RestaurantService {

    private final Logger log = LoggerFactory.getLogger(RestaurantService.class);

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Save a restaurant.
     *
     * @param restaurant the entity to save.
     * @return the persisted entity.
     */
    public Restaurant save(Restaurant restaurant) {
        log.debug("Request to save Restaurant : {}", restaurant);
        return restaurantRepository.save(restaurant);
    }

    /**
     * Update a restaurant.
     *
     * @param restaurant the entity to save.
     * @return the persisted entity.
     */
    public Restaurant update(Restaurant restaurant) {
        log.debug("Request to save Restaurant : {}", restaurant);
        return restaurantRepository.save(restaurant);
    }

    /**
     * Partially update a restaurant.
     *
     * @param restaurant the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Restaurant> partialUpdate(Restaurant restaurant) {
        log.debug("Request to partially update Restaurant : {}", restaurant);

        return restaurantRepository
            .findById(restaurant.getId())
            .map(existingRestaurant -> {
                if (restaurant.getNom() != null) {
                    existingRestaurant.setNom(restaurant.getNom());
                }
                if (restaurant.getDescription() != null) {
                    existingRestaurant.setDescription(restaurant.getDescription());
                }
                if (restaurant.getTags() != null) {
                    existingRestaurant.setTags(restaurant.getTags());
                }
                if (restaurant.getAdresse() != null) {
                    existingRestaurant.setAdresse(restaurant.getAdresse());
                }
                if (restaurant.getFraisLivraison() != null) {
                    existingRestaurant.setFraisLivraison(restaurant.getFraisLivraison());
                }
                if (restaurant.getHeureOUverture() != null) {
                    existingRestaurant.setHeureOUverture(restaurant.getHeureOUverture());
                }
                if (restaurant.getHeureFermeture() != null) {
                    existingRestaurant.setHeureFermeture(restaurant.getHeureFermeture());
                }
                if (restaurant.getEvaluation() != null) {
                    existingRestaurant.setEvaluation(restaurant.getEvaluation());
                }

                return existingRestaurant;
            })
            .map(restaurantRepository::save);
    }

    /**
     * Get all the restaurants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Restaurant> findAll(Pageable pageable) {
        log.debug("Request to get all Restaurants");
        return restaurantRepository.findAll(pageable);
    }

    /**
     * Get one restaurant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Restaurant> findOne(Long id) {
        log.debug("Request to get Restaurant : {}", id);
        return restaurantRepository.findById(id);
    }

    /**
     * Delete the restaurant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Restaurant : {}", id);
        restaurantRepository.deleteById(id);
    }
}
