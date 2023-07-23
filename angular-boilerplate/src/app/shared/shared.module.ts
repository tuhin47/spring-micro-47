// Angular modules
import { CommonModule }                     from '@angular/common';
import { NgModule }                         from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule }                     from '@angular/router';

// External modules
import { TranslateModule }      from '@ngx-translate/core';
import { AngularSvgIconModule } from 'angular-svg-icon';
import { NgbModule }            from '@ng-bootstrap/ng-bootstrap';

// Components
import { ToastComponent }       from '@blocks/toast/toast.component';
import { ProgressBarComponent } from '@blocks/progress-bar/progress-bar.component';

// Forms
import { FormConfirmComponent } from '@forms/form-confirm/form-confirm.component';

// Modals
import { ModalWrapperComponent } from '@modals/modal-wrapper/modal-wrapper.component';

// Layouts
import { LayoutHeaderComponent } from '@layouts/layout-header/layout-header.component';
import { PageLayoutComponent }   from '@layouts/page-layout/page-layout.component';

// Pipes
// Directives
import { ModalWrapperDirective } from '@directives/modal-wrapper.directive';
import { RippleModule }          from 'primeng/ripple';

//PrimeNG
import { TableModule }   from 'primeng/table';
import { ButtonModule }  from 'primeng/button';
import { TooltipModule } from 'primeng/tooltip';


@NgModule({
  imports:
    [
      // Angular modules
      CommonModule,
      RouterModule,
      FormsModule,
      ReactiveFormsModule,

      // External modules
      TranslateModule,
      AngularSvgIconModule,
      NgbModule,
    ],
  declarations:
    [
      // Components
      ToastComponent,
      ProgressBarComponent,

      // Forms
      FormConfirmComponent,

      // Modals
      ModalWrapperComponent,

      // Layouts
      LayoutHeaderComponent,
      PageLayoutComponent,

      // Pipes

      // Directives
      ModalWrapperDirective
    ],
  exports:
    [
      // Angular modules
      CommonModule,
      RouterModule,
      FormsModule,
      ReactiveFormsModule,

      // External modules
      TranslateModule,
      AngularSvgIconModule,
      NgbModule,

      // Components
      ToastComponent,
      ProgressBarComponent,

      // Forms
      FormConfirmComponent,

      // Modals
      ModalWrapperComponent,

      // Layouts
      LayoutHeaderComponent,
      PageLayoutComponent,

      // Pipes

      // Directives
      ModalWrapperDirective,

      //Primeng
      TableModule,
      ButtonModule,
      RippleModule,
      TooltipModule,
    ],
  providers:
    []
})
export class SharedModule {
}
