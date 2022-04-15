package com.mycompany.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.coopcycle.domain.Commande} entity.
 */
public class CommandeDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer quantite;

    @DecimalMin(value = "0")
    private Float total;

    private PanierDTO panier;

    private MenuDTO menu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public PanierDTO getPanier() {
        return panier;
    }

    public void setPanier(PanierDTO panier) {
        this.panier = panier;
    }

    public MenuDTO getMenu() {
        return menu;
    }

    public void setMenu(MenuDTO menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeDTO)) {
            return false;
        }

        CommandeDTO commandeDTO = (CommandeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commandeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeDTO{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", total=" + getTotal() +
            ", panier=" + getPanier() +
            ", menu=" + getMenu() +
            "}";
    }
}
