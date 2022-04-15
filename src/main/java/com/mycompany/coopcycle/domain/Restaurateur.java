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
 * A Restaurateur.
 */
@Entity
@Table(name = "restaurateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Restaurateur implements Serializable {

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

    @Column(name = "commissions")
    private Float commissions;

    @Column(name = "est_dg")
    private Boolean estDG;

    @Column(name = "est_menbre_ca")
    private Boolean estMenbreCA;

    @OneToMany(mappedBy = "restaurateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "menus", "restaurateur" }, allowSetters = true)
    private Set<Restaurant> restaurants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "restaurateurs", "clients", "livreurs" }, allowSetters = true)
    private Cooperative cooperative;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Restaurateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Restaurateur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public Restaurateur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public Restaurateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Restaurateur phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getCommissions() {
        return this.commissions;
    }

    public Restaurateur commissions(Float commissions) {
        this.setCommissions(commissions);
        return this;
    }

    public void setCommissions(Float commissions) {
        this.commissions = commissions;
    }

    public Boolean getEstDG() {
        return this.estDG;
    }

    public Restaurateur estDG(Boolean estDG) {
        this.setEstDG(estDG);
        return this;
    }

    public void setEstDG(Boolean estDG) {
        this.estDG = estDG;
    }

    public Boolean getEstMenbreCA() {
        return this.estMenbreCA;
    }

    public Restaurateur estMenbreCA(Boolean estMenbreCA) {
        this.setEstMenbreCA(estMenbreCA);
        return this;
    }

    public void setEstMenbreCA(Boolean estMenbreCA) {
        this.estMenbreCA = estMenbreCA;
    }

    public Set<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        if (this.restaurants != null) {
            this.restaurants.forEach(i -> i.setRestaurateur(null));
        }
        if (restaurants != null) {
            restaurants.forEach(i -> i.setRestaurateur(this));
        }
        this.restaurants = restaurants;
    }

    public Restaurateur restaurants(Set<Restaurant> restaurants) {
        this.setRestaurants(restaurants);
        return this;
    }

    public Restaurateur addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
        restaurant.setRestaurateur(this);
        return this;
    }

    public Restaurateur removeRestaurant(Restaurant restaurant) {
        this.restaurants.remove(restaurant);
        restaurant.setRestaurateur(null);
        return this;
    }

    public Cooperative getCooperative() {
        return this.cooperative;
    }

    public void setCooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
    }

    public Restaurateur cooperative(Cooperative cooperative) {
        this.setCooperative(cooperative);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurateur)) {
            return false;
        }
        return id != null && id.equals(((Restaurateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurateur{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", commissions=" + getCommissions() +
            ", estDG='" + getEstDG() + "'" +
            ", estMenbreCA='" + getEstMenbreCA() + "'" +
            "}";
    }
}
