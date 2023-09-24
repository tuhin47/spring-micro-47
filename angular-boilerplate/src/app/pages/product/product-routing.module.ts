import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ProductsComponent} from "./products/products.component";
import { authGuard } from '@helpers/auth.guard';


const routes: Routes = [
  {
    path: '',
    component: ProductsComponent,
    canActivate: [authGuard],
    data: { roles: ["ROLE_ADMIN"] }
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule {
}
