import { ICommande } from 'app/entities/commande/commande.model';
import { IRestaurant } from 'app/entities/restaurant/restaurant.model';

export interface IMenu {
  id?: number;
  nom?: string;
  description?: string | null;
  prix?: number;
  commandes?: ICommande[] | null;
  restaurant?: IRestaurant | null;
}

export class Menu implements IMenu {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string | null,
    public prix?: number,
    public commandes?: ICommande[] | null,
    public restaurant?: IRestaurant | null
  ) {}
}

export function getMenuIdentifier(menu: IMenu): number | undefined {
  return menu.id;
}
