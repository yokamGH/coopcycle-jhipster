import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPanier, Panier } from '../panier.model';
import { PanierService } from '../service/panier.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { ILivreur } from 'app/entities/livreur/livreur.model';
import { LivreurService } from 'app/entities/livreur/service/livreur.service';
import { State } from 'app/entities/enumerations/state.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

@Component({
  selector: 'jhi-panier-update',
  templateUrl: './panier-update.component.html',
})
export class PanierUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);
  paymentMethodValues = Object.keys(PaymentMethod);

  clientsSharedCollection: IClient[] = [];
  livreursSharedCollection: ILivreur[] = [];

  editForm = this.fb.group({
    id: [],
    dateCommande: [null, [Validators.required]],
    adresseLivraison: [null, [Validators.required]],
    fraisService: [null, [Validators.required, Validators.min(0)]],
    netAPayer: [null, [Validators.min(0)]],
    state: [null, [Validators.required]],
    datePaiement: [null, [Validators.required]],
    methodePaiement: [null, [Validators.required]],
    client: [],
    livreur: [],
  });

  constructor(
    protected panierService: PanierService,
    protected clientService: ClientService,
    protected livreurService: LivreurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ panier }) => {
      if (panier.id === undefined) {
        const today = dayjs().startOf('day');
        panier.dateCommande = today;
        panier.datePaiement = today;
      }

      this.updateForm(panier);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const panier = this.createFromForm();
    if (panier.id !== undefined) {
      this.subscribeToSaveResponse(this.panierService.update(panier));
    } else {
      this.subscribeToSaveResponse(this.panierService.create(panier));
    }
  }

  trackClientById(_index: number, item: IClient): number {
    return item.id!;
  }

  trackLivreurById(_index: number, item: ILivreur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPanier>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(panier: IPanier): void {
    this.editForm.patchValue({
      id: panier.id,
      dateCommande: panier.dateCommande ? panier.dateCommande.format(DATE_TIME_FORMAT) : null,
      adresseLivraison: panier.adresseLivraison,
      fraisService: panier.fraisService,
      netAPayer: panier.netAPayer,
      state: panier.state,
      datePaiement: panier.datePaiement ? panier.datePaiement.format(DATE_TIME_FORMAT) : null,
      methodePaiement: panier.methodePaiement,
      client: panier.client,
      livreur: panier.livreur,
    });

    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing(this.clientsSharedCollection, panier.client);
    this.livreursSharedCollection = this.livreurService.addLivreurToCollectionIfMissing(this.livreursSharedCollection, panier.livreur);
  }

  protected loadRelationshipsOptions(): void {
    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing(clients, this.editForm.get('client')!.value)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));

    this.livreurService
      .query()
      .pipe(map((res: HttpResponse<ILivreur[]>) => res.body ?? []))
      .pipe(
        map((livreurs: ILivreur[]) => this.livreurService.addLivreurToCollectionIfMissing(livreurs, this.editForm.get('livreur')!.value))
      )
      .subscribe((livreurs: ILivreur[]) => (this.livreursSharedCollection = livreurs));
  }

  protected createFromForm(): IPanier {
    return {
      ...new Panier(),
      id: this.editForm.get(['id'])!.value,
      dateCommande: this.editForm.get(['dateCommande'])!.value
        ? dayjs(this.editForm.get(['dateCommande'])!.value, DATE_TIME_FORMAT)
        : undefined,
      adresseLivraison: this.editForm.get(['adresseLivraison'])!.value,
      fraisService: this.editForm.get(['fraisService'])!.value,
      netAPayer: this.editForm.get(['netAPayer'])!.value,
      state: this.editForm.get(['state'])!.value,
      datePaiement: this.editForm.get(['datePaiement'])!.value
        ? dayjs(this.editForm.get(['datePaiement'])!.value, DATE_TIME_FORMAT)
        : undefined,
      methodePaiement: this.editForm.get(['methodePaiement'])!.value,
      client: this.editForm.get(['client'])!.value,
      livreur: this.editForm.get(['livreur'])!.value,
    };
  }
}
