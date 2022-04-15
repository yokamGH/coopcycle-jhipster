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
 * A Cooperative.
 */
@Entity
@Table(name = "cooperative")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cooperative implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "localisation", nullable = false)
    private String localisation;

    @Min(value = 0)
    @Column(name = "nb_adherents")
    private Integer nbAdherents;

    @OneToMany(mappedBy = "cooperative")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "restaurants", "cooperative" }, allowSetters = true)
    private Set<Restaurateur> restaurateurs = new HashSet<>();

    @OneToMany(mappedBy = "cooperative")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paniers", "cooperative" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    @OneToMany(mappedBy = "cooperative")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paniers", "cooperative" }, allowSetters = true)
    private Set<Livreur> livreurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cooperative id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Cooperative nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalisation() {
        return this.localisation;
    }

    public Cooperative localisation(String localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Integer getNbAdherents() {
        return this.nbAdherents;
    }

    public Cooperative nbAdherents(Integer nbAdherents) {
        this.setNbAdherents(nbAdherents);
        return this;
    }

    public void setNbAdherents(Integer nbAdherents) {
        this.nbAdherents = nbAdherents;
    }

    public Set<Restaurateur> getRestaurateurs() {
        return this.restaurateurs;
    }

    public void setRestaurateurs(Set<Restaurateur> restaurateurs) {
        if (this.restaurateurs != null) {
            this.restaurateurs.forEach(i -> i.setCooperative(null));
        }
        if (restaurateurs != null) {
            restaurateurs.forEach(i -> i.setCooperative(this));
        }
        this.restaurateurs = restaurateurs;
    }

    public Cooperative restaurateurs(Set<Restaurateur> restaurateurs) {
        this.setRestaurateurs(restaurateurs);
        return this;
    }

    public Cooperative addRestaurateur(Restaurateur restaurateur) {
        this.restaurateurs.add(restaurateur);
        restaurateur.setCooperative(this);
        return this;
    }

    public Cooperative removeRestaurateur(Restaurateur restaurateur) {
        this.restaurateurs.remove(restaurateur);
        restaurateur.setCooperative(null);
        return this;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.setCooperative(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setCooperative(this));
        }
        this.clients = clients;
    }

    public Cooperative clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public Cooperative addClient(Client client) {
        this.clients.add(client);
        client.setCooperative(this);
        return this;
    }

    public Cooperative removeClient(Client client) {
        this.clients.remove(client);
        client.setCooperative(null);
        return this;
    }

    public Set<Livreur> getLivreurs() {
        return this.livreurs;
    }

    public void setLivreurs(Set<Livreur> livreurs) {
        if (this.livreurs != null) {
            this.livreurs.forEach(i -> i.setCooperative(null));
        }
        if (livreurs != null) {
            livreurs.forEach(i -> i.setCooperative(this));
        }
        this.livreurs = livreurs;
    }

    public Cooperative livreurs(Set<Livreur> livreurs) {
        this.setLivreurs(livreurs);
        return this;
    }

    public Cooperative addLivreur(Livreur livreur) {
        this.livreurs.add(livreur);
        livreur.setCooperative(this);
        return this;
    }

    public Cooperative removeLivreur(Livreur livreur) {
        this.livreurs.remove(livreur);
        livreur.setCooperative(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cooperative)) {
            return false;
        }
        return id != null && id.equals(((Cooperative) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cooperative{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", localisation='" + getLocalisation() + "'" +
            ", nbAdherents=" + getNbAdherents() +
            "}";
    }
}
