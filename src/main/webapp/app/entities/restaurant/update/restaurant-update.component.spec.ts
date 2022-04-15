import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RestaurantService } from '../service/restaurant.service';
import { IRestaurant, Restaurant } from '../restaurant.model';
import { IRestaurateur } from 'app/entities/restaurateur/restaurateur.model';
import { RestaurateurService } from 'app/entities/restaurateur/service/restaurateur.service';

import { RestaurantUpdateComponent } from './restaurant-update.component';

describe('Restaurant Management Update Component', () => {
  let comp: RestaurantUpdateComponent;
  let fixture: ComponentFixture<RestaurantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let restaurantService: RestaurantService;
  let restaurateurService: RestaurateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RestaurantUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RestaurantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RestaurantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    restaurantService = TestBed.inject(RestaurantService);
    restaurateurService = TestBed.inject(RestaurateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Restaurateur query and add missing value', () => {
      const restaurant: IRestaurant = { id: 456 };
      const restaurateur: IRestaurateur = { id: 69960 };
      restaurant.restaurateur = restaurateur;

      const restaurateurCollection: IRestaurateur[] = [{ id: 1715 }];
      jest.spyOn(restaurateurService, 'query').mockReturnValue(of(new HttpResponse({ body: restaurateurCollection })));
      const additionalRestaurateurs = [restaurateur];
      const expectedCollection: IRestaurateur[] = [...additionalRestaurateurs, ...restaurateurCollection];
      jest.spyOn(restaurateurService, 'addRestaurateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ restaurant });
      comp.ngOnInit();

      expect(restaurateurService.query).toHaveBeenCalled();
      expect(restaurateurService.addRestaurateurToCollectionIfMissing).toHaveBeenCalledWith(
        restaurateurCollection,
        ...additionalRestaurateurs
      );
      expect(comp.restaurateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const restaurant: IRestaurant = { id: 456 };
      const restaurateur: IRestaurateur = { id: 73549 };
      restaurant.restaurateur = restaurateur;

      activatedRoute.data = of({ restaurant });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(restaurant));
      expect(comp.restaurateursSharedCollection).toContain(restaurateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Restaurant>>();
      const restaurant = { id: 123 };
      jest.spyOn(restaurantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ restaurant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: restaurant }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(restaurantService.update).toHaveBeenCalledWith(restaurant);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Restaurant>>();
      const restaurant = new Restaurant();
      jest.spyOn(restaurantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ restaurant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: restaurant }));
      saveSubject.complete();

      // THEN
      expect(restaurantService.create).toHaveBeenCalledWith(restaurant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Restaurant>>();
      const restaurant = { id: 123 };
      jest.spyOn(restaurantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ restaurant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(restaurantService.update).toHaveBeenCalledWith(restaurant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRestaurateurById', () => {
      it('Should return tracked Restaurateur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRestaurateurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
