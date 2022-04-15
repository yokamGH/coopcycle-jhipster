package com.mycompany.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.coopcycle.domain.enumeration.PaymentMethod;
import com.mycompany.coopcycle.domain.enumeration.State;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Panier.
 */
@Entity
@Table(name = "panier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Panier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_commande", nullable = false)
    private Instant dateCommande;

    @NotNull
    @Column(name = "adresse_livraison", nullable = false)
    private String adresseLivraison;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "frais_service", nullable = false)
    private Float fraisService;

    @Min(value = 0)
    @Column(name = "net_a_payer")
    private Integer netAPayer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @NotNull
    @Column(name = "date_paiement", nullable = false)
    private Instant datePaiement;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "methode_paiement", nullable = false)
    private PaymentMethod methodePaiement;

    @OneToMany(mappedBy = "panier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "panier", "menu" }, allowSetters = true)
    private Set<Commande> commandes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "paniers", "cooperative" }, allowSetters = true)
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties(value = { "paniers", "cooperative" }, allowSetters = true)
    private Livreur livreur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Panier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCommande() {
        return this.dateCommande;
    }

    public Panier dateCommande(Instant dateCommande) {
        this.setDateCommande(dateCommande);
        return this;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getAdresseLivraison() {
        return this.adresseLivraison;
    }

    public Panier adresseLivraison(String adresseLivraison) {
        this.setAdresseLivraison(adresseLivraison);
        return this;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public Float getFraisService() {
        return this.fraisService;
    }

    public Panier fraisService(Float fraisService) {
        this.setFraisService(fraisService);
        return this;
    }

    public void setFraisService(Float fraisService) {
        this.fraisService = fraisService;
    }

    public Integer getNetAPayer() {
        return this.netAPayer;
    }

    public Panier netAPayer(Integer netAPayer) {
        this.setNetAPayer(netAPayer);
        return this;
    }

    public void setNetAPayer(Integer netAPayer) {
        this.netAPayer = netAPayer;
    }

    public State getState() {
        return this.state;
    }

    public Panier state(State state) {
        this.setState(state);
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Instant getDatePaiement() {
        return this.datePaiement;
    }

    public Panier datePaiement(Instant datePaiement) {
        this.setDatePaiement(datePaiement);
        return this;
    }

    public void setDatePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
    }

    public PaymentMethod getMethodePaiement() {
        return this.methodePaiement;
    }

    public Panier methodePaiement(PaymentMethod methodePaiement) {
        this.setMethodePaiement(methodePaiement);
        return this;
    }

    public void setMethodePaiement(PaymentMethod methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public Set<Commande> getCommandes() {
        return this.commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        if (this.commandes != null) {
            this.commandes.forEach(i -> i.setPanier(null));
        }
        if (commandes != null) {
            commandes.forEach(i -> i.setPanier(this));
        }
        this.commandes = commandes;
    }

    public Panier commandes(Set<Commande> commandes) {
        this.setCommandes(commandes);
        return this;
    }

    public Panier addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setPanier(this);
        return this;
    }

    public Panier removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setPanier(null);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Panier client(Client client) {
        this.setClient(client);
        return this;
    }

    public Livreur getLivreur() {
        return this.livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public Panier livreur(Livreur livreur) {
        this.setLivreur(livreur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Panier)) {
            return false;
        }
        return id != null && id.equals(((Panier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Panier{" +
            "id=" + getId() +
            ", dateCommande='" + getDateCommande() + "'" +
            ", adresseLivraison='" + getAdresseLivraison() + "'" +
            ", fraisService=" + getFraisService() +
            ", netAPayer=" + getNetAPayer() +
            ", state='" + getState() + "'" +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", methodePaiement='" + getMethodePaiement() + "'" +
            "}";
    }
}
