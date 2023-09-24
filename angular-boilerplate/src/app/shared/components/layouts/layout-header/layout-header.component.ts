// Angular modules
import { Component, DoCheck, OnInit } from '@angular/core';

// Internal modules
import { environment }     from '@env/environment';
import { StorageHelper }   from '@helpers/storage.helper';
import { EventBusService } from 'src/app/shared/event/event-bus.service';
import { EventData }       from 'src/app/shared/event/event.class';

@Component({
  selector    : 'app-layout-header',
  templateUrl : './layout-header.component.html',
  styleUrls   : ['./layout-header.component.scss']
})
export class LayoutHeaderComponent implements OnInit
{
  public appName         : string  = environment.appName;
  public isMenuCollapsed : boolean = true;
  public authenticated  = false;

  constructor
  (
    private eventBusService : EventBusService,
  )
  {

  }

  public ngOnInit() : void
  {
    this.authenticated = StorageHelper.getUser()?.authenticated || false
  }

  // -------------------------------------------------------------------------------
  // NOTE Init ---------------------------------------------------------------------
  // -------------------------------------------------------------------------------

  // -------------------------------------------------------------------------------
  // NOTE Actions ------------------------------------------------------------------
  // -------------------------------------------------------------------------------

  public async onClickLogout() : Promise<void>
  {
    this.eventBusService.emit(new EventData('logout', null));
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
