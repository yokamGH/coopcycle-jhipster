import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PanierService } from '../service/panier.service';
import { IPanier, Panier } from '../panier.model';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { ILivreur } from 'app/entities/livreur/livreur.model';
import { LivreurService } from 'app/entities/livreur/service/livreur.service';

import { PanierUpdateComponent } from './panier-update.component';

describe('Panier Management Update Component', () => {
  let comp: PanierUpdateComponent;
  let fixture: ComponentFixture<PanierUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let panierService: PanierService;
  let clientService: ClientService;
  let livreurService: LivreurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PanierUpdateComponent],
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
      .overrideTemplate(PanierUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PanierUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    panierService = TestBed.inject(PanierService);
    clientService = TestBed.inject(ClientService);
    livreurService = TestBed.inject(LivreurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Client query and add missing value', () => {
      const panier: IPanier = { id: 456 };
      const client: IClient = { id: 43249 };
      panier.client = client;

      const clientCollection: IClient[] = [{ id: 28698 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ panier });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(clientCollection, ...additionalClients);
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Livreur query and add missing value', () => {
      const panier: IPanier = { id: 456 };
      const livreur: ILivreur = { id: 18364 };
      panier.livreur = livreur;

      const livreurCollection: ILivreur[] = [{ id: 84758 }];
      jest.spyOn(livreurService, 'query').mockReturnValue(of(new HttpResponse({ body: livreurCollection })));
      const additionalLivreurs = [livreur];
      const expectedCollection: ILivreur[] = [...additionalLivreurs, ...livreurCollection];
      jest.spyOn(livreurService, 'addLivreurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ panier });
      comp.ngOnInit();

      expect(livreurService.query).toHaveBeenCalled();
      expect(livreurService.addLivreurToCollectionIfMissing).toHaveBeenCalledWith(livreurCollection, ...additionalLivreurs);
      expect(comp.livreursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const panier: IPanier = { id: 456 };
      const client: IClient = { id: 12503 };
      panier.client = client;
      const livreur: ILivreur = { id: 94856 };
      panier.livreur = livreur;

      activatedRoute.data = of({ panier });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(panier));
      expect(comp.clientsSharedCollection).toContain(client);
      expect(comp.livreursSharedCollection).toContain(livreur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Panier>>();
      const panier = { id: 123 };
      jest.spyOn(panierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ panier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: panier }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(panierService.update).toHaveBeenCalledWith(panier);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Panier>>();
      const panier = new Panier();
      jest.spyOn(panierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ panier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: panier }));
      saveSubject.complete();

      // THEN
      expect(panierService.create).toHaveBeenCalledWith(panier);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Panier>>();
      const panier = { id: 123 };
      jest.spyOn(panierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ panier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(panierService.update).toHaveBeenCalledWith(panier);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackClientById', () => {
      it('Should return tracked Client primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLivreurById', () => {
      it('Should return tracked Livreur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLivreurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
