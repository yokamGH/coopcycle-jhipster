import dayjs from 'dayjs/esm';
import { ICommande } from 'app/entities/commande/commande.model';
import { IClient } from 'app/entities/client/client.model';
import { ILivreur } from 'app/entities/livreur/livreur.model';
import { State } from 'app/entities/enumerations/state.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

export interface IPanier {
  id?: number;
  dateCommande?: dayjs.Dayjs;
  adresseLivraison?: string;
  fraisService?: number;
  netAPayer?: number | null;
  state?: State;
  datePaiement?: dayjs.Dayjs;
  methodePaiement?: PaymentMethod;
  commandes?: ICommande[] | null;
  client?: IClient | null;
  livreur?: ILivreur | null;
}

export class Panier implements IPanier {
  constructor(
    public id?: number,
    public dateCommande?: dayjs.Dayjs,
    public adresseLivraison?: string,
    public fraisService?: number,
    public netAPayer?: number | null,
    public state?: State,
    public datePaiement?: dayjs.Dayjs,
    public methodePaiement?: PaymentMethod,
    public commandes?: ICommande[] | null,
    public client?: IClient | null,
    public livreur?: ILivreur | null
  ) {}
}

export function getPanierIdentifier(panier: IPanier): number | undefined {
  return panier.id;
}
