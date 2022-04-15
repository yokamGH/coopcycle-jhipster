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
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "tags")
    private String tags;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "frais_livraison", nullable = false)
    private Float fraisLivraison;

    @NotNull
    @Column(name = "heure_o_uverture", nullable = false)
    private String heureOUverture;

    @NotNull
    @Column(name = "heure_fermeture", nullable = false)
    private String heureFermeture;

    @DecimalMin(value = "0")
    @Column(name = "evaluation")
    private Float evaluation;

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commandes", "restaurant" }, allowSetters = true)
    private Set<Menu> menus = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "restaurants", "cooperative" }, allowSetters = true)
    private Restaurateur restaurateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Restaurant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Restaurant nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Restaurant description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return this.tags;
    }

    public Restaurant tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Restaurant adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Float getFraisLivraison() {
        return this.fraisLivraison;
    }

    public Restaurant fraisLivraison(Float fraisLivraison) {
        this.setFraisLivraison(fraisLivraison);
        return this;
    }

    public void setFraisLivraison(Float fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public String getHeureOUverture() {
        return this.heureOUverture;
    }

    public Restaurant heureOUverture(String heureOUverture) {
        this.setHeureOUverture(heureOUverture);
        return this;
    }

    public void setHeureOUverture(String heureOUverture) {
        this.heureOUverture = heureOUverture;
    }

    public String getHeureFermeture() {
        return this.heureFermeture;
    }

    public Restaurant heureFermeture(String heureFermeture) {
        this.setHeureFermeture(heureFermeture);
        return this;
    }

    public void setHeureFermeture(String heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public Float getEvaluation() {
        return this.evaluation;
    }

    public Restaurant evaluation(Float evaluation) {
        this.setEvaluation(evaluation);
        return this;
    }

    public void setEvaluation(Float evaluation) {
        this.evaluation = evaluation;
    }

    public Set<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(Set<Menu> menus) {
        if (this.menus != null) {
            this.menus.forEach(i -> i.setRestaurant(null));
        }
        if (menus != null) {
            menus.forEach(i -> i.setRestaurant(this));
        }
        this.menus = menus;
    }

    public Restaurant menus(Set<Menu> menus) {
        this.setMenus(menus);
        return this;
    }

    public Restaurant addMenu(Menu menu) {
        this.menus.add(menu);
        menu.setRestaurant(this);
        return this;
    }

    public Restaurant removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.setRestaurant(null);
        return this;
    }

    public Restaurateur getRestaurateur() {
        return this.restaurateur;
    }

    public void setRestaurateur(Restaurateur restaurateur) {
        this.restaurateur = restaurateur;
    }

    public Restaurant restaurateur(Restaurateur restaurateur) {
        this.setRestaurateur(restaurateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", tags='" + getTags() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", fraisLivraison=" + getFraisLivraison() +
            ", heureOUverture='" + getHeureOUverture() + "'" +
            ", heureFermeture='" + getHeureFermeture() + "'" +
            ", evaluation=" + getEvaluation() +
            "}";
    }
}
