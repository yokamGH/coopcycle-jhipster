import { IPanier } from 'app/entities/panier/panier.model';
import { IMenu } from 'app/entities/menu/menu.model';

export interface ICommande {
  id?: number;
  quantite?: number;
  total?: number | null;
  panier?: IPanier | null;
  menu?: IMenu | null;
}

export class Commande implements ICommande {
  constructor(
    public id?: number,
    public quantite?: number,
    public total?: number | null,
    public panier?: IPanier | null,
    public menu?: IMenu | null
  ) {}
}

export function getCommandeIdentifier(commande: ICommande): number | undefined {
  return commande.id;
}
