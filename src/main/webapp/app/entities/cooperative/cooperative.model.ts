import { IRestaurateur } from 'app/entities/restaurateur/restaurateur.model';
import { IClient } from 'app/entities/client/client.model';
import { ILivreur } from 'app/entities/livreur/livreur.model';

export interface ICooperative {
  id?: number;
  nom?: string;
  localisation?: string;
  nbAdherents?: number | null;
  restaurateurs?: IRestaurateur[] | null;
  clients?: IClient[] | null;
  livreurs?: ILivreur[] | null;
}

export class Cooperative implements ICooperative {
  constructor(
    public id?: number,
    public nom?: string,
    public localisation?: string,
    public nbAdherents?: number | null,
    public restaurateurs?: IRestaurateur[] | null,
    public clients?: IClient[] | null,
    public livreurs?: ILivreur[] | null
  ) {}
}

export function getCooperativeIdentifier(cooperative: ICooperative): number | undefined {
  return cooperative.id;
}
