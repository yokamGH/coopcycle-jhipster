import { IRestaurant } from 'app/entities/restaurant/restaurant.model';
import { ICooperative } from 'app/entities/cooperative/cooperative.model';

export interface IRestaurateur {
  id?: number;
  prenom?: string | null;
  nom?: string;
  email?: string;
  phoneNumber?: string;
  commissions?: number | null;
  estDG?: boolean | null;
  estMenbreCA?: boolean | null;
  restaurants?: IRestaurant[] | null;
  cooperative?: ICooperative | null;
}

export class Restaurateur implements IRestaurateur {
  constructor(
    public id?: number,
    public prenom?: string | null,
    public nom?: string,
    public email?: string,
    public phoneNumber?: string,
    public commissions?: number | null,
    public estDG?: boolean | null,
    public estMenbreCA?: boolean | null,
    public restaurants?: IRestaurant[] | null,
    public cooperative?: ICooperative | null
  ) {
    this.estDG = this.estDG ?? false;
    this.estMenbreCA = this.estMenbreCA ?? false;
  }
}

export function getRestaurateurIdentifier(restaurateur: IRestaurateur): number | undefined {
  return restaurateur.id;
}
