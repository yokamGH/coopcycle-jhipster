package com.mycompany.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.coopcycle.domain.Cooperative} entity.
 */
public class CooperativeDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String localisation;

    @Min(value = 0)
    private Integer nbAdherents;

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

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Integer getNbAdherents() {
        return nbAdherents;
    }

    public void setNbAdherents(Integer nbAdherents) {
        this.nbAdherents = nbAdherents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CooperativeDTO)) {
            return false;
        }

        CooperativeDTO cooperativeDTO = (CooperativeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cooperativeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CooperativeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", localisation='" + getLocalisation() + "'" +
            ", nbAdherents=" + getNbAdherents() +
            "}";
    }
}
