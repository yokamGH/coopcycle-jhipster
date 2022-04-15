package com.mycompany.coopcycle.repository;

import com.mycompany.coopcycle.domain.Restaurateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Restaurateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurateurRepository extends JpaRepository<Restaurateur, Long> {}
