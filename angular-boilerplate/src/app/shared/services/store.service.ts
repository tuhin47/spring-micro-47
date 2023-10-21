// Angular modules
import { Injectable }       from '@angular/core';

// External modules
import { TranslateService } from '@ngx-translate/core';
import { BehaviorSubject }  from 'rxjs';

// Internal modules
import { environment }      from '@env/environment';

/**
 * Angular Application State Management: You Do (Not) Need External Data Stores like NgRx, Redux, and Other
 * https://www.maestralsolutions.com/angular-application-state-management-you-do-not-need-external-data-stores/
 */
@Injectable()
export class StoreService
{
  // NOTE Data
  private readonly _pageTitleSource = new BehaviorSubject<string>(environment.appName);
  private readonly _isLoadingSource = new BehaviorSubject<boolean>(false);

  // NOTE Events
  readonly pageTitle$ = this._pageTitleSource.asObservable();
  readonly isLoading$ = this._isLoadingSource.asObservable();

  // -------------------------------------------------------------------------------
  // NOTE Is loading ---------------------------------------------------------------
  // -------------------------------------------------------------------------------

  public setIsLoading(state : boolean) : void
  {
    this._isLoadingSource.next(state);
  }

}
