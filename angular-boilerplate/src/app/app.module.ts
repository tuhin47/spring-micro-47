// Angular modules
import { DatePipe }                            from '@angular/common';
import { HttpClient, HttpClientModule }        from '@angular/common/http';
import { APP_INITIALIZER, Injector, NgModule } from '@angular/core';
import { BrowserModule }                       from '@angular/platform-browser';
import { BrowserAnimationsModule }             from '@angular/platform-browser/animations';

// Factories
import { appInitFactory }           from '@factories/app-init.factory';
import { authInterceptorProviders } from '@helpers/auth.interceptor';

// External modules
import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader }                                from '@ngx-translate/http-loader';

// Services
import { AppService }           from '@services/app.service';
import { StoreService }         from '@services/store.service';
import { AngularSvgIconModule } from 'angular-svg-icon';

// Internal modules
import { AppRoutingModule } from './app-routing.module';

// Components
import { AppComponent } from './app.component';
import { SharedModule } from './shared/shared.module';

@NgModule({
  imports: [
    // Angular modules
    HttpClientModule,
    BrowserAnimationsModule,
    BrowserModule,

    // External modules
    TranslateModule.forRoot({
      loader:
        {
          provide: TranslateLoader,
          useFactory: (createTranslateLoader),
          deps: [HttpClient]
        }
    }),
    AngularSvgIconModule.forRoot(),

    // Internal modules
    SharedModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent
  ],
  providers: [
    // External modules
    {
      provide: APP_INITIALIZER,
      useFactory: appInitFactory,
      deps: [TranslateService, Injector],
      multi: true
    },

    // Services
    AppService,
    StoreService,

    // Pipes
    DatePipe,

    // Guards

    // Interceptors
    authInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}
