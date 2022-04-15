import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RestaurateurService } from '../service/restaurateur.service';
import { IRestaurateur, Restaurateur } from '../restaurateur.model';
import { ICooperative } from 'app/entities/cooperative/cooperative.model';
import { CooperativeService } from 'app/entities/cooperative/service/cooperative.service';

import { RestaurateurUpdateComponent } from './restaurateur-update.component';

describe('Restaurateur Management Update Component', () => {
  let comp: RestaurateurUpdateComponent;
  let fixture: ComponentFixture<RestaurateurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let restaurateurService: RestaurateurService;
  let cooperativeService: CooperativeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RestaurateurUpdateComponent],
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
      .overrideTemplate(RestaurateurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RestaurateurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    restaurateurService = TestBed.inject(RestaurateurService);
    cooperativeService = TestBed.inject(CooperativeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cooperative query and add missing value', () => {
      const restaurateur: IRestaurateur = { id: 456 };
      const cooperative: ICooperative = { id: 61948 };
      restaurateur.cooperative = cooperative;

      const cooperativeCollection: ICooperative[] = [{ id: 49771 }];
      jest.spyOn(cooperativeService, 'query').mockReturnValue(of(new HttpResponse({ body: cooperativeCollection })));
      const additionalCooperatives = [cooperative];
      const expectedCollection: ICooperative[] = [...additionalCooperatives, ...cooperativeCollection];
      jest.spyOn(cooperativeService, 'addCooperativeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ restaurateur });
      comp.ngOnInit();

      expect(cooperativeService.query).toHaveBeenCalled();
      expect(cooperativeService.addCooperativeToCollectionIfMissing).toHaveBeenCalledWith(cooperativeCollection, ...additionalCooperatives);
      expect(comp.cooperativesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const restaurateur: IRestaurateur = { id: 456 };
      const cooperative: ICooperative = { id: 16124 };
      restaurateur.cooperative = cooperative;

      activatedRoute.data = of({ restaurateur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(restaurateur));
      expect(comp.cooperativesSharedCollection).toContain(cooperative);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Restaurateur>>();
      const restaurateur = { id: 123 };
      jest.spyOn(restaurateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ restaurateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: restaurateur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(restaurateurService.update).toHaveBeenCalledWith(restaurateur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Restaurateur>>();
      const restaurateur = new Restaurateur();
      jest.spyOn(restaurateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ restaurateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: restaurateur }));
      saveSubject.complete();

      // THEN
      expect(restaurateurService.create).toHaveBeenCalledWith(restaurateur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Restaurateur>>();
      const restaurateur = { id: 123 };
      jest.spyOn(restaurateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ restaurateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(restaurateurService.update).toHaveBeenCalledWith(restaurateur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCooperativeById', () => {
      it('Should return tracked Cooperative primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCooperativeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
