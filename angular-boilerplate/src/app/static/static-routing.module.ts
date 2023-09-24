// Angular modules
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClockComponent }       from './clock/clock.component';

const routes : Routes = [
  {
    path      : '',
    component : ClockComponent,
    children  : [
      {
        path      : 'clock',
        component : ClockComponent
      },
    ]
  }
];

@NgModule({
  imports : [ RouterModule.forChild(routes) ],
  exports : [ RouterModule ]
})
export class StaticRoutingModule { }
