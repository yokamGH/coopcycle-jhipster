import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRestaurateur, Restaurateur } from '../restaurateur.model';
import { RestaurateurService } from '../service/restaurateur.service';
import { ICooperative } from 'app/entities/cooperative/cooperative.model';
import { CooperativeService } from 'app/entities/cooperative/service/cooperative.service';

@Component({
  selector: 'jhi-restaurateur-update',
  templateUrl: './restaurateur-update.component.html',
})
export class RestaurateurUpdateComponent implements OnInit {
  isSaving = false;

  cooperativesSharedCollection: ICooperative[] = [];

  editForm = this.fb.group({
    id: [],
    prenom: [],
    nom: [null, [Validators.required]],
    email: [null, [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    phoneNumber: [null, [Validators.required]],
    commissions: [null, [Validators.min(0)]],
    estDG: [],
    estMenbreCA: [],
    cooperative: [],
  });

  constructor(
    protected restaurateurService: RestaurateurService,
    protected cooperativeService: CooperativeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ restaurateur }) => {
      this.updateForm(restaurateur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const restaurateur = this.createFromForm();
    if (restaurateur.id !== undefined) {
      this.subscribeToSaveResponse(this.restaurateurService.update(restaurateur));
    } else {
      this.subscribeToSaveResponse(this.restaurateurService.create(restaurateur));
    }
  }

  trackCooperativeById(_index: number, item: ICooperative): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurateur>>): void {
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

  protected updateForm(restaurateur: IRestaurateur): void {
    this.editForm.patchValue({
      id: restaurateur.id,
      prenom: restaurateur.prenom,
      nom: restaurateur.nom,
      email: restaurateur.email,
      phoneNumber: restaurateur.phoneNumber,
      commissions: restaurateur.commissions,
      estDG: restaurateur.estDG,
      estMenbreCA: restaurateur.estMenbreCA,
      cooperative: restaurateur.cooperative,
    });

    this.cooperativesSharedCollection = this.cooperativeService.addCooperativeToCollectionIfMissing(
      this.cooperativesSharedCollection,
      restaurateur.cooperative
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cooperativeService
      .query()
      .pipe(map((res: HttpResponse<ICooperative[]>) => res.body ?? []))
      .pipe(
        map((cooperatives: ICooperative[]) =>
          this.cooperativeService.addCooperativeToCollectionIfMissing(cooperatives, this.editForm.get('cooperative')!.value)
        )
      )
      .subscribe((cooperatives: ICooperative[]) => (this.cooperativesSharedCollection = cooperatives));
  }

  protected createFromForm(): IRestaurateur {
    return {
      ...new Restaurateur(),
      id: this.editForm.get(['id'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      commissions: this.editForm.get(['commissions'])!.value,
      estDG: this.editForm.get(['estDG'])!.value,
      estMenbreCA: this.editForm.get(['estMenbreCA'])!.value,
      cooperative: this.editForm.get(['cooperative'])!.value,
    };
  }
}
