package com.mycompany.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.coopcycle.domain.Restaurant} entity.
 */
public class RestaurantDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String description;

    private String tags;

    @NotNull
    private String adresse;

    @NotNull
    @DecimalMin(value = "0")
    private Float fraisLivraison;

    @NotNull
    private String heureOUverture;

    @NotNull
    private String heureFermeture;

    @DecimalMin(value = "0")
    private Float evaluation;

    private RestaurateurDTO restaurateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Float getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(Float fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public String getHeureOUverture() {
        return heureOUverture;
    }

    public void setHeureOUverture(String heureOUverture) {
        this.heureOUverture = heureOUverture;
    }

    public String getHeureFermeture() {
        return heureFermeture;
    }

    public void setHeureFermeture(String heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public Float getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Float evaluation) {
        this.evaluation = evaluation;
    }

    public RestaurateurDTO getRestaurateur() {
        return restaurateur;
    }

    public void setRestaurateur(RestaurateurDTO restaurateur) {
        this.restaurateur = restaurateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestaurantDTO)) {
            return false;
        }

        RestaurantDTO restaurantDTO = (RestaurantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, restaurantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurantDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", tags='" + getTags() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", fraisLivraison=" + getFraisLivraison() +
            ", heureOUverture='" + getHeureOUverture() + "'" +
            ", heureFermeture='" + getHeureFermeture() + "'" +
            ", evaluation=" + getEvaluation() +
            ", restaurateur=" + getRestaurateur() +
            "}";
    }
}
