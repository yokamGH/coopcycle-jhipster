import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILivreur, Livreur } from '../livreur.model';
import { LivreurService } from '../service/livreur.service';

@Injectable({ providedIn: 'root' })
export class LivreurRoutingResolveService implements Resolve<ILivreur> {
  constructor(protected service: LivreurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivreur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((livreur: HttpResponse<Livreur>) => {
          if (livreur.body) {
            return of(livreur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Livreur());
  }
}
