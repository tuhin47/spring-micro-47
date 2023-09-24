// Angular modules
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

// Internal modules
import { SharedModule }   from '../shared/shared.module';
import { ClockComponent } from './clock/clock.component';

// Components
import { StaticRoutingModule }   from './static-routing.module';
import { NotFoundComponent }     from './not-found/not-found.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';

@NgModule({
  imports:
    [
      SharedModule,
      StaticRoutingModule
    ],
  declarations:
    [
      NotFoundComponent,
      AccessDeniedComponent,
      ClockComponent
    ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StaticModule { }
