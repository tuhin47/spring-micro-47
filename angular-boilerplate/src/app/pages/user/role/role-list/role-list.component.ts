import { loadRoles }              from '@actions/role.actions';
import { Component, OnInit }      from '@angular/core';
import { EventBusService }        from '@event/event-bus.service';
import { EVENT_TYPES, EventData } from '@event/event.class';
import { Role }                   from '@models/role.model';
import { Store }                  from '@ngrx/store';
import { RoleState }              from '@reducers/role.reducer';
import { Observable }             from 'rxjs';

@Component({
  selector: 'app-role-list',
  templateUrl: './role-list.component.html'
})
export class RoleListComponent implements OnInit {
  roles$: Observable<Role[]>;

  constructor(private store: Store<{ role: RoleState }>, private eventBusService: EventBusService) {
    this.roles$ = store.select(state => state.role.roles);
  }

  ngOnInit(): void {
    this.store.dispatch(loadRoles());
  }

  updateRole(role: Role | undefined) {
    this.eventBusService.emit(new EventData(role ? EVENT_TYPES.EDIT_ROLE : EVENT_TYPES.ADD_ROLE, role));
  }
}
