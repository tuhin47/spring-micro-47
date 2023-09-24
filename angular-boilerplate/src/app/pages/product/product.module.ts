import { CommonModule }                     from '@angular/common';
import {  NgModule } from '@angular/core';
import { SharedModule }                     from '../../shared/shared.module';
import { ProductRoutingModule } from './product-routing.module';
import { ProductListComponent } from './products/product-list/product-list.component';
import { ProductsComponent }    from './products/products.component';


@NgModule({
  declarations: [
    ProductsComponent,
    ProductListComponent
  ],
  imports: [
    CommonModule,
    ProductRoutingModule,
    SharedModule,
  ]
})
export class ProductModule {
}
