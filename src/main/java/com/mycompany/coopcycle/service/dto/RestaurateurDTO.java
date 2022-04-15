package com.mycompany.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.coopcycle.domain.Restaurateur} entity.
 */
public class RestaurateurDTO implements Serializable {

    private Long id;

    private String prenom;

    @NotNull
    private String nom;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    @NotNull
    private String phoneNumber;

    @DecimalMin(value = "0")
    private Float commissions;

    private Boolean estDG;

    private Boolean estMenbreCA;

    private CooperativeDTO cooperative;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getCommissions() {
        return commissions;
    }

    public void setCommissions(Float commissions) {
        this.commissions = commissions;
    }

    public Boolean getEstDG() {
        return estDG;
    }

    public void setEstDG(Boolean estDG) {
        this.estDG = estDG;
    }

    public Boolean getEstMenbreCA() {
        return estMenbreCA;
    }

    public void setEstMenbreCA(Boolean estMenbreCA) {
        this.estMenbreCA = estMenbreCA;
    }

    public CooperativeDTO getCooperative() {
        return cooperative;
    }

    public void setCooperative(CooperativeDTO cooperative) {
        this.cooperative = cooperative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestaurateurDTO)) {
            return false;
        }

        RestaurateurDTO restaurateurDTO = (RestaurateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, restaurateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurateurDTO{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", commissions=" + getCommissions() +
            ", estDG='" + getEstDG() + "'" +
            ", estMenbreCA='" + getEstMenbreCA() + "'" +
            ", cooperative=" + getCooperative() +
            "}";
    }
}
