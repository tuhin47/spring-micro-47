import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard }            from '@helpers/auth.guard';
import { MenuListComponent }    from './menu-list/menu-list.component';


const routes: Routes = [
  {
    path: '',
    component: MenuListComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN']},
  },
  {
    path: 'edit/:id',
    component: MenuListComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN']}
  }

];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MenuRoutingModule {
}
