import { IPanier } from 'app/entities/panier/panier.model';
import { ICooperative } from 'app/entities/cooperative/cooperative.model';

export interface IClient {
  id?: number;
  prenom?: string | null;
  nom?: string;
  email?: string;
  phoneNumber?: string | null;
  estDG?: boolean | null;
  estMenbreCA?: boolean | null;
  paniers?: IPanier[] | null;
  cooperative?: ICooperative | null;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public prenom?: string | null,
    public nom?: string,
    public email?: string,
    public phoneNumber?: string | null,
    public estDG?: boolean | null,
    public estMenbreCA?: boolean | null,
    public paniers?: IPanier[] | null,
    public cooperative?: ICooperative | null
  ) {
    this.estDG = this.estDG ?? false;
    this.estMenbreCA = this.estMenbreCA ?? false;
  }
}

export function getClientIdentifier(client: IClient): number | undefined {
  return client.id;
}
