// Angular modules
import { NgModule }      from '@angular/core';
import { RouterModule }  from '@angular/router';
import { Routes }        from '@angular/router';

// Components
import { HomeComponent } from './home.component';

const routes: Routes = [
  { path : '', component : HomeComponent },
];

@NgModule({
  imports : [ RouterModule.forChild(routes) ],
  exports : [ RouterModule ]
})
export class HomeRoutingModule { }
