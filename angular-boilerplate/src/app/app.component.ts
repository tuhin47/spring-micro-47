// Angular modules
import { Component }        from '@angular/core';
import { OnInit }           from '@angular/core';

// External modules
import { TranslateService } from '@ngx-translate/core';
import {EventBusService} from "./shared/event/event-bus.service";
import {Subscription} from "rxjs";
import { Router } from '@angular/router';
import { StorageHelper } from '@helpers/storage.helper';

@Component({
  selector    : 'app-root',
  templateUrl : './app.component.html',
  styleUrls   : ['./app.component.scss']
})
export class AppComponent implements OnInit
{
  constructor
  (
    private translateService : TranslateService,
    private eventBusService: EventBusService,
    private router : Router,

  )
  {
    // NOTE This language will be used as a fallback when a translation isn't found in the current language
    this.translateService.setDefaultLang('en');
    // NOTE The lang to use, if the lang isn't available, it will use the current loader to get them
    // let userLanguage = StorageHelper.getLanguage();
    // if (!userLanguage)
    let userLanguage = 'en';
    this.translateService.use(userLanguage);
  }

  // -------------------------------------------------------------------------------
  // NOTE Init ---------------------------------------------------------------------
  // -------------------------------------------------------------------------------

  eventBusSub?: Subscription;
  public ngOnInit() : void
  {
    this.eventBusSub = this.eventBusService.on('logout', () => {
      this.logoutAction();

    });
     this.eventBusService.on('denied', () => {
      if(StorageHelper.getToken() != null){
        this.router.navigate(['/access-denied']);
      }
      else{
        this.logoutAction();
      }
    });
  }

  // -------------------------------------------------------------------------------
  // NOTE Actions ------------------------------------------------------------------
  // -------------------------------------------------------------------------------


  logoutAction() {
    StorageHelper.removeToken();
    this.router.navigate(['/auth/login']);
  }
  // -------------------------------------------------------------------------------
  // NOTE Computed props -----------------------------------------------------------
  // -------------------------------------------------------------------------------

  // -------------------------------------------------------------------------------
  // NOTE Helpers ------------------------------------------------------------------
  // -------------------------------------------------------------------------------

  // -------------------------------------------------------------------------------
  // NOTE Requests -----------------------------------------------------------------
  // -------------------------------------------------------------------------------

  // -------------------------------------------------------------------------------
  // NOTE Subscriptions ------------------------------------------------------------
  // -------------------------------------------------------------------------------
}
