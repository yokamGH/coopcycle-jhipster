import { IMenu } from 'app/entities/menu/menu.model';
import { IRestaurateur } from 'app/entities/restaurateur/restaurateur.model';

export interface IRestaurant {
  id?: number;
  nom?: string;
  description?: string | null;
  tags?: string | null;
  adresse?: string;
  fraisLivraison?: number;
  heureOUverture?: string;
  heureFermeture?: string;
  evaluation?: number | null;
  menus?: IMenu[] | null;
  restaurateur?: IRestaurateur | null;
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string | null,
    public tags?: string | null,
    public adresse?: string,
    public fraisLivraison?: number,
    public heureOUverture?: string,
    public heureFermeture?: string,
    public evaluation?: number | null,
    public menus?: IMenu[] | null,
    public restaurateur?: IRestaurateur | null
  ) {}
}

export function getRestaurantIdentifier(restaurant: IRestaurant): number | undefined {
  return restaurant.id;
}
