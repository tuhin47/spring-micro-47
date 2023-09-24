// Angular modules
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Components
import { authGuard } from '@helpers/auth.guard';
import { HomeComponent } from './home.component';

const routes: Routes = [
  {
    path: '', component: HomeComponent,
    canActivate: [authGuard],
    data: { roles: ["ROLE_ADMIN"] }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
