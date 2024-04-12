import { CommonModule }                     from '@angular/common';
import { NgModule }                         from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule }                     from '@angular/router';
import { ProgressBarComponent }             from '@blocks/progress-bar/progress-bar.component';
import { ToastComponent }                   from '@blocks/toast/toast.component';
import { ModalWrapperDirective }            from '@directives/modal-wrapper.directive';
import { FormConfirmComponent }             from '@forms/form-confirm/form-confirm.component';
import { FontAwesomeModule }                from '@fortawesome/angular-fontawesome';
import { LayoutHeaderComponent }            from '@layouts/layout-header/layout-header.component';
import { PageLayoutComponent }              from '@layouts/page-layout/page-layout.component';
import { SideMenuComponent }                from '@layouts/side-menu/side-menu.component';
import { ModalWrapperComponent }            from '@modals/modal-wrapper/modal-wrapper.component';
import { NgbModule }                        from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule }                  from '@ngx-translate/core';
import { AngularSvgIconModule }             from 'angular-svg-icon';
import { ButtonModule }                     from 'primeng/button';
import { RippleModule }                     from 'primeng/ripple';
import { SidebarModule }                    from 'primeng/sidebar';
import { TableModule }                      from 'primeng/table';
import { TooltipModule }                    from 'primeng/tooltip';
import { TreeModule }                       from 'primeng/tree';

@NgModule({
  imports:
    [
      AngularSvgIconModule,
      ButtonModule,
      CommonModule,
      FontAwesomeModule,
      FormsModule,
      NgbModule,
      ReactiveFormsModule,
      RouterModule,
      SidebarModule,
      TranslateModule,
      TreeModule,
    ],
  declarations:
    [
      FormConfirmComponent,
      LayoutHeaderComponent,
      ModalWrapperComponent,
      ModalWrapperDirective,
      PageLayoutComponent,
      ProgressBarComponent,
      SideMenuComponent,
      SideMenuComponent,
      ToastComponent,
    ],
  exports:
    [
      AngularSvgIconModule,
      ButtonModule,
      CommonModule,
      FormConfirmComponent,
      FormsModule,
      LayoutHeaderComponent,
      ModalWrapperComponent,
      ModalWrapperDirective,
      NgbModule,
      PageLayoutComponent,
      ProgressBarComponent,
      ReactiveFormsModule,
      RippleModule,
      RouterModule,
      TableModule,
      ToastComponent,
      TooltipModule,
      TranslateModule,
    ],
  providers:
    []
})
export class SharedModule {
}