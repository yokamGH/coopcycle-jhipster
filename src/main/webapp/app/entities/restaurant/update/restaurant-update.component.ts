import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRestaurant, Restaurant } from '../restaurant.model';
import { RestaurantService } from '../service/restaurant.service';
import { IRestaurateur } from 'app/entities/restaurateur/restaurateur.model';
import { RestaurateurService } from 'app/entities/restaurateur/service/restaurateur.service';

@Component({
  selector: 'jhi-restaurant-update',
  templateUrl: './restaurant-update.component.html',
})
export class RestaurantUpdateComponent implements OnInit {
  isSaving = false;

  restaurateursSharedCollection: IRestaurateur[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    tags: [],
    adresse: [null, [Validators.required]],
    fraisLivraison: [null, [Validators.required, Validators.min(0)]],
    heureOUverture: [null, [Validators.required]],
    heureFermeture: [null, [Validators.required]],
    evaluation: [null, [Validators.min(0)]],
    restaurateur: [],
  });

  constructor(
    protected restaurantService: RestaurantService,
    protected restaurateurService: RestaurateurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ restaurant }) => {
      this.updateForm(restaurant);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const restaurant = this.createFromForm();
    if (restaurant.id !== undefined) {
      this.subscribeToSaveResponse(this.restaurantService.update(restaurant));
    } else {
      this.subscribeToSaveResponse(this.restaurantService.create(restaurant));
    }
  }

  trackRestaurateurById(_index: number, item: IRestaurateur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurant>>): void {
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

  protected updateForm(restaurant: IRestaurant): void {
    this.editForm.patchValue({
      id: restaurant.id,
      nom: restaurant.nom,
      description: restaurant.description,
      tags: restaurant.tags,
      adresse: restaurant.adresse,
      fraisLivraison: restaurant.fraisLivraison,
      heureOUverture: restaurant.heureOUverture,
      heureFermeture: restaurant.heureFermeture,
      evaluation: restaurant.evaluation,
      restaurateur: restaurant.restaurateur,
    });

    this.restaurateursSharedCollection = this.restaurateurService.addRestaurateurToCollectionIfMissing(
      this.restaurateursSharedCollection,
      restaurant.restaurateur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.restaurateurService
      .query()
      .pipe(map((res: HttpResponse<IRestaurateur[]>) => res.body ?? []))
      .pipe(
        map((restaurateurs: IRestaurateur[]) =>
          this.restaurateurService.addRestaurateurToCollectionIfMissing(restaurateurs, this.editForm.get('restaurateur')!.value)
        )
      )
      .subscribe((restaurateurs: IRestaurateur[]) => (this.restaurateursSharedCollection = restaurateurs));
  }

  protected createFromForm(): IRestaurant {
    return {
      ...new Restaurant(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      tags: this.editForm.get(['tags'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      fraisLivraison: this.editForm.get(['fraisLivraison'])!.value,
      heureOUverture: this.editForm.get(['heureOUverture'])!.value,
      heureFermeture: this.editForm.get(['heureFermeture'])!.value,
      evaluation: this.editForm.get(['evaluation'])!.value,
      restaurateur: this.editForm.get(['restaurateur'])!.value,
    };
  }
}
