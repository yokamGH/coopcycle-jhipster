package com.mycompany.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Livreur.
 */
@Entity
@Table(name = "livreur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Livreur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @DecimalMin(value = "0")
    @Column(name = "commissions")
    private Float commissions;

    @DecimalMin(value = "0")
    @Column(name = "nb_etoiles")
    private Float nbEtoiles;

    @Column(name = "est_dg")
    private Boolean estDG;

    @Column(name = "est_menbre_ca")
    private Boolean estMenbreCA;

    @OneToMany(mappedBy = "livreur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commandes", "client", "livreur" }, allowSetters = true)
    private Set<Panier> paniers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "restaurateurs", "clients", "livreurs" }, allowSetters = true)
    private Cooperative cooperative;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Livreur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Livreur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public Livreur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public Livreur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Livreur phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getCommissions() {
        return this.commissions;
    }

    public Livreur commissions(Float commissions) {
        this.setCommissions(commissions);
        return this;
    }

    public void setCommissions(Float commissions) {
        this.commissions = commissions;
    }

    public Float getNbEtoiles() {
        return this.nbEtoiles;
    }

    public Livreur nbEtoiles(Float nbEtoiles) {
        this.setNbEtoiles(nbEtoiles);
        return this;
    }

    public void setNbEtoiles(Float nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public Boolean getEstDG() {
        return this.estDG;
    }

    public Livreur estDG(Boolean estDG) {
        this.setEstDG(estDG);
        return this;
    }

    public void setEstDG(Boolean estDG) {
        this.estDG = estDG;
    }

    public Boolean getEstMenbreCA() {
        return this.estMenbreCA;
    }

    public Livreur estMenbreCA(Boolean estMenbreCA) {
        this.setEstMenbreCA(estMenbreCA);
        return this;
    }

    public void setEstMenbreCA(Boolean estMenbreCA) {
        this.estMenbreCA = estMenbreCA;
    }

    public Set<Panier> getPaniers() {
        return this.paniers;
    }

    public void setPaniers(Set<Panier> paniers) {
        if (this.paniers != null) {
            this.paniers.forEach(i -> i.setLivreur(null));
        }
        if (paniers != null) {
            paniers.forEach(i -> i.setLivreur(this));
        }
        this.paniers = paniers;
    }

    public Livreur paniers(Set<Panier> paniers) {
        this.setPaniers(paniers);
        return this;
    }

    public Livreur addPanier(Panier panier) {
        this.paniers.add(panier);
        panier.setLivreur(this);
        return this;
    }

    public Livreur removePanier(Panier panier) {
        this.paniers.remove(panier);
        panier.setLivreur(null);
        return this;
    }

    public Cooperative getCooperative() {
        return this.cooperative;
    }

    public void setCooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
    }

    public Livreur cooperative(Cooperative cooperative) {
        this.setCooperative(cooperative);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livreur)) {
            return false;
        }
        return id != null && id.equals(((Livreur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Livreur{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", commissions=" + getCommissions() +
            ", nbEtoiles=" + getNbEtoiles() +
            ", estDG='" + getEstDG() + "'" +
            ", estMenbreCA='" + getEstMenbreCA() + "'" +
            "}";
    }
}
