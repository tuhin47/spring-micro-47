import { CommonModule }      from '@angular/common';
import { NgModule }          from '@angular/core';
import { DialogModule }      from 'primeng/dialog';
import { SharedModule }      from '../../shared/shared.module';
import { MenuFormComponent } from './menu-form/menu-form.component';
import { MenuListComponent } from './menu-list/menu-list.component';
import { MenuRoutingModule } from './menu-routing.module';


@NgModule({
  declarations: [
    MenuListComponent,
    MenuFormComponent
  ],
  imports: [
    CommonModule,
    MenuRoutingModule,
    SharedModule,
    DialogModule,
  ]
})
export class MenuModule {
}
