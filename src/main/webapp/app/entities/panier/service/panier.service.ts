import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPanier, getPanierIdentifier } from '../panier.model';

export type EntityResponseType = HttpResponse<IPanier>;
export type EntityArrayResponseType = HttpResponse<IPanier[]>;

@Injectable({ providedIn: 'root' })
export class PanierService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/paniers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(panier: IPanier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(panier);
    return this.http
      .post<IPanier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(panier: IPanier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(panier);
    return this.http
      .put<IPanier>(`${this.resourceUrl}/${getPanierIdentifier(panier) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(panier: IPanier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(panier);
    return this.http
      .patch<IPanier>(`${this.resourceUrl}/${getPanierIdentifier(panier) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPanier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPanier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPanierToCollectionIfMissing(panierCollection: IPanier[], ...paniersToCheck: (IPanier | null | undefined)[]): IPanier[] {
    const paniers: IPanier[] = paniersToCheck.filter(isPresent);
    if (paniers.length > 0) {
      const panierCollectionIdentifiers = panierCollection.map(panierItem => getPanierIdentifier(panierItem)!);
      const paniersToAdd = paniers.filter(panierItem => {
        const panierIdentifier = getPanierIdentifier(panierItem);
        if (panierIdentifier == null || panierCollectionIdentifiers.includes(panierIdentifier)) {
          return false;
        }
        panierCollectionIdentifiers.push(panierIdentifier);
        return true;
      });
      return [...paniersToAdd, ...panierCollection];
    }
    return panierCollection;
  }

  protected convertDateFromClient(panier: IPanier): IPanier {
    return Object.assign({}, panier, {
      dateCommande: panier.dateCommande?.isValid() ? panier.dateCommande.toJSON() : undefined,
      datePaiement: panier.datePaiement?.isValid() ? panier.datePaiement.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCommande = res.body.dateCommande ? dayjs(res.body.dateCommande) : undefined;
      res.body.datePaiement = res.body.datePaiement ? dayjs(res.body.datePaiement) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((panier: IPanier) => {
        panier.dateCommande = panier.dateCommande ? dayjs(panier.dateCommande) : undefined;
        panier.datePaiement = panier.datePaiement ? dayjs(panier.datePaiement) : undefined;
      });
    }
    return res;
  }
}
