import { loadRoles }         from '@actions/role.actions';
import { loadUserRoles }     from '@actions/user.actions';
import { Component, OnInit } from '@angular/core';
import { EventBusService }   from '@event/event-bus.service';
import { EVENT_TYPES }       from '@event/event.class';
import { Role }              from '@models/role.model';
import { UserInfo }          from '@models/user.model';
import { Store }             from '@ngrx/store';
import { RoleState }         from '@reducers/role.reducer';
import { UserState }         from '@reducers/user.reducer';
import { Observable }        from 'rxjs';

@Component({
  selector: 'app-role-checklist',
  templateUrl: './role-checklist.component.html',
})
export class RoleChecklistComponent implements OnInit {

  _user?: UserInfo;
  roles$: Observable<Role[]>;
  selectedRoles: Role[] = [];
  selectedRoleIds: number[] = [];

  constructor(private store: Store<{ role: RoleState, user: UserState }>, private eventBusService: EventBusService) {
    this.roles$ = store.select(state => state.role.roles);
    this.store.select(state => state.user.roles)
        .subscribe(roles => {
          this.selectedRoles = roles
          this.selectedRoleIds = roles.map(role => role.id);
        });

    this.eventBusService.on(EVENT_TYPES.USER_SELECTED, (user: UserInfo) => {
      this.user = user;
    })
  }

  ngOnInit(): void {
    this.loadRoles();
  }

  set user(value: UserInfo) {
    this._user = value;
    if (value) {
      this.store.dispatch(loadUserRoles({userId: value.id}));
    } else {
      this.clearSelected();
    }
  }

  private clearSelected() {
    this.selectedRoles = [];
    this.selectedRoleIds = [];
  }

  loadRoles(): void {
    this.store.dispatch(loadRoles());
  }

}
