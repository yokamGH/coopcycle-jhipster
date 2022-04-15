import { IPanier } from 'app/entities/panier/panier.model';
import { ICooperative } from 'app/entities/cooperative/cooperative.model';

export interface ILivreur {
  id?: number;
  prenom?: string;
  nom?: string | null;
  email?: string;
  phoneNumber?: string;
  commissions?: number | null;
  nbEtoiles?: number | null;
  estDG?: boolean | null;
  estMenbreCA?: boolean | null;
  paniers?: IPanier[] | null;
  cooperative?: ICooperative | null;
}

export class Livreur implements ILivreur {
  constructor(
    public id?: number,
    public prenom?: string,
    public nom?: string | null,
    public email?: string,
    public phoneNumber?: string,
    public commissions?: number | null,
    public nbEtoiles?: number | null,
    public estDG?: boolean | null,
    public estMenbreCA?: boolean | null,
    public paniers?: IPanier[] | null,
    public cooperative?: ICooperative | null
  ) {
    this.estDG = this.estDG ?? false;
    this.estMenbreCA = this.estMenbreCA ?? false;
  }
}

export function getLivreurIdentifier(livreur: ILivreur): number | undefined {
  return livreur.id;
}
