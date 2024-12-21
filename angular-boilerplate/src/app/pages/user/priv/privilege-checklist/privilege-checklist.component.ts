import { loadPrivileges }     from '@actions/priv.actions';
import { loadUserPrivileges } from '@actions/user.actions';
import { Component, OnInit }  from '@angular/core';
import { EventBusService }    from '@event/event-bus.service';
import { EVENT_TYPES }        from '@event/event.class';
import { Privilege }          from '@models/privilege.model';
import { UserInfo }           from '@models/user.model';
import { Store }              from '@ngrx/store';
import { PrivilegeState }     from '@reducers/priv.reducer';
import { UserState }          from '@reducers/user.reducer';
import { Observable }         from 'rxjs';

@Component({
  selector: 'app-privilege-checklist',
  templateUrl: './privilege-checklist.component.html',
})
export class PrivilegeChecklistComponent implements OnInit {

  _user?: UserInfo;
  privileges$: Observable<Privilege[]>;
  selectedPrivileges: Privilege[] = [];

  constructor(private store: Store<{
    priv: PrivilegeState,
    user: UserState
  }>, private eventBusService: EventBusService) {
    this.privileges$ = store.select(state => state.priv.privileges);
    this.store.select(state => state.user.privileges)
        .subscribe(privileges => {
          this.selectedPrivileges = privileges;
        });

    this.eventBusService.on(EVENT_TYPES.USER_SELECTED, (user: UserInfo) => {
      this.user = user;
    });
  }

  ngOnInit(): void {
    this.loadPrivileges();
  }

  set user(value: UserInfo) {
    this._user = value;
    if (value) {
      this.store.dispatch(loadUserPrivileges({userId: value.id}));
    } else {
      this.clearSelected();
    }
  }

  private clearSelected() {
    this.selectedPrivileges = [];
  }

  loadPrivileges(): void {
    this.store.dispatch(loadPrivileges())
  }
}
