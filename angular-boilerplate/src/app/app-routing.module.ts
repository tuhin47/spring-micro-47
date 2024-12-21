// Angular modules
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Components
import { AccessDeniedComponent } from './static/access-denied/access-denied.component';
import { NotFoundComponent }     from './static/not-found/not-found.component';

const routes: Routes = [

  {path: 'admin', loadChildren: () => import('./pages/user/user.module').then(m => m.UserModule),},
  {path: 'auth', loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule),},
  {path: 'chat', loadChildren: () => import('./pages/chat/chat.module').then(m => m.ChatModule),},
  {path: 'common', loadChildren: () => import('./static/static.module').then(m => m.StaticModule),},
  {path: 'home', loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule),},
  {path: 'menu', loadChildren: () => import('./pages/menu/menu.module').then(m => m.MenuModule),},
  {path: 'product', loadChildren: () => import('./pages/product/product.module').then(m => m.ProductModule),},
  {
    path: 'access-denied',
    component: AccessDeniedComponent,
  },
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
