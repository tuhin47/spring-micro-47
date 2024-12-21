import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard }            from '@helpers/auth.guard';
import { RoleComponent }        from './role/role.component';
import { UserComponent }        from './user/user.component';

const routes: Routes = [
  {
    path: '',
    component: UserComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN']},
  },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN']},
  },
  {
    path: 'role',
    component: RoleComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN']},
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {
}
