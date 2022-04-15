import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommandeService } from '../service/commande.service';
import { ICommande, Commande } from '../commande.model';
import { IPanier } from 'app/entities/panier/panier.model';
import { PanierService } from 'app/entities/panier/service/panier.service';
import { IMenu } from 'app/entities/menu/menu.model';
import { MenuService } from 'app/entities/menu/service/menu.service';

import { CommandeUpdateComponent } from './commande-update.component';

describe('Commande Management Update Component', () => {
  let comp: CommandeUpdateComponent;
  let fixture: ComponentFixture<CommandeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commandeService: CommandeService;
  let panierService: PanierService;
  let menuService: MenuService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommandeUpdateComponent],
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
      .overrideTemplate(CommandeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommandeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commandeService = TestBed.inject(CommandeService);
    panierService = TestBed.inject(PanierService);
    menuService = TestBed.inject(MenuService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Panier query and add missing value', () => {
      const commande: ICommande = { id: 456 };
      const panier: IPanier = { id: 82318 };
      commande.panier = panier;

      const panierCollection: IPanier[] = [{ id: 64761 }];
      jest.spyOn(panierService, 'query').mockReturnValue(of(new HttpResponse({ body: panierCollection })));
      const additionalPaniers = [panier];
      const expectedCollection: IPanier[] = [...additionalPaniers, ...panierCollection];
      jest.spyOn(panierService, 'addPanierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commande });
      comp.ngOnInit();

      expect(panierService.query).toHaveBeenCalled();
      expect(panierService.addPanierToCollectionIfMissing).toHaveBeenCalledWith(panierCollection, ...additionalPaniers);
      expect(comp.paniersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Menu query and add missing value', () => {
      const commande: ICommande = { id: 456 };
      const menu: IMenu = { id: 82834 };
      commande.menu = menu;

      const menuCollection: IMenu[] = [{ id: 74279 }];
      jest.spyOn(menuService, 'query').mockReturnValue(of(new HttpResponse({ body: menuCollection })));
      const additionalMenus = [menu];
      const expectedCollection: IMenu[] = [...additionalMenus, ...menuCollection];
      jest.spyOn(menuService, 'addMenuToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commande });
      comp.ngOnInit();

      expect(menuService.query).toHaveBeenCalled();
      expect(menuService.addMenuToCollectionIfMissing).toHaveBeenCalledWith(menuCollection, ...additionalMenus);
      expect(comp.menusSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const commande: ICommande = { id: 456 };
      const panier: IPanier = { id: 63803 };
      commande.panier = panier;
      const menu: IMenu = { id: 50982 };
      commande.menu = menu;

      activatedRoute.data = of({ commande });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(commande));
      expect(comp.paniersSharedCollection).toContain(panier);
      expect(comp.menusSharedCollection).toContain(menu);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Commande>>();
      const commande = { id: 123 };
      jest.spyOn(commandeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commande }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(commandeService.update).toHaveBeenCalledWith(commande);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Commande>>();
      const commande = new Commande();
      jest.spyOn(commandeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commande }));
      saveSubject.complete();

      // THEN
      expect(commandeService.create).toHaveBeenCalledWith(commande);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Commande>>();
      const commande = { id: 123 };
      jest.spyOn(commandeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commandeService.update).toHaveBeenCalledWith(commande);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPanierById', () => {
      it('Should return tracked Panier primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPanierById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackMenuById', () => {
      it('Should return tracked Menu primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMenuById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
