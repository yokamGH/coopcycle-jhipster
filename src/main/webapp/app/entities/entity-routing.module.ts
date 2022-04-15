import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'panier',
        data: { pageTitle: 'coopcycleApp.panier.home.title' },
        loadChildren: () => import('./panier/panier.module').then(m => m.PanierModule),
      },
      {
        path: 'commande',
        data: { pageTitle: 'coopcycleApp.commande.home.title' },
        loadChildren: () => import('./commande/commande.module').then(m => m.CommandeModule),
      },
      {
        path: 'menu',
        data: { pageTitle: 'coopcycleApp.menu.home.title' },
        loadChildren: () => import('./menu/menu.module').then(m => m.MenuModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'coopcycleApp.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'restaurateur',
        data: { pageTitle: 'coopcycleApp.restaurateur.home.title' },
        loadChildren: () => import('./restaurateur/restaurateur.module').then(m => m.RestaurateurModule),
      },
      {
        path: 'restaurant',
        data: { pageTitle: 'coopcycleApp.restaurant.home.title' },
        loadChildren: () => import('./restaurant/restaurant.module').then(m => m.RestaurantModule),
      },
      {
        path: 'livreur',
        data: { pageTitle: 'coopcycleApp.livreur.home.title' },
        loadChildren: () => import('./livreur/livreur.module').then(m => m.LivreurModule),
      },
      {
        path: 'cooperative',
        data: { pageTitle: 'coopcycleApp.cooperative.home.title' },
        loadChildren: () => import('./cooperative/cooperative.module').then(m => m.CooperativeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
