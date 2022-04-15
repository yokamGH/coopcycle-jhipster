package com.mycompany.coopcycle.service.dto;

import com.mycompany.coopcycle.domain.enumeration.PaymentMethod;
import com.mycompany.coopcycle.domain.enumeration.State;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.coopcycle.domain.Panier} entity.
 */
public class PanierDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dateCommande;

    @NotNull
    private String adresseLivraison;

    @NotNull
    @DecimalMin(value = "0")
    private Float fraisService;

    @Min(value = 0)
    private Integer netAPayer;

    @NotNull
    private State state;

    @NotNull
    private Instant datePaiement;

    @NotNull
    private PaymentMethod methodePaiement;

    private ClientDTO client;

    private LivreurDTO livreur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public Float getFraisService() {
        return fraisService;
    }

    public void setFraisService(Float fraisService) {
        this.fraisService = fraisService;
    }

    public Integer getNetAPayer() {
        return netAPayer;
    }

    public void setNetAPayer(Integer netAPayer) {
        this.netAPayer = netAPayer;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Instant getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
    }

    public PaymentMethod getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(PaymentMethod methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public LivreurDTO getLivreur() {
        return livreur;
    }

    public void setLivreur(LivreurDTO livreur) {
        this.livreur = livreur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PanierDTO)) {
            return false;
        }

        PanierDTO panierDTO = (PanierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, panierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PanierDTO{" +
            "id=" + getId() +
            ", dateCommande='" + getDateCommande() + "'" +
            ", adresseLivraison='" + getAdresseLivraison() + "'" +
            ", fraisService=" + getFraisService() +
            ", netAPayer=" + getNetAPayer() +
            ", state='" + getState() + "'" +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", methodePaiement='" + getMethodePaiement() + "'" +
            ", client=" + getClient() +
            ", livreur=" + getLivreur() +
            "}";
    }
}
