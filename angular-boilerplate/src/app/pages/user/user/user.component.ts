import { loadUsers }              from '@actions/user.actions';
import { Component, OnInit }      from '@angular/core';
import { EventBusService }        from '@event/event-bus.service';
import { EVENT_TYPES, EventData } from '@event/event.class';
import { Privilege }              from '@models/privilege.model';
import { UserInfo }               from '@models/user.model';
import { Store }                  from '@ngrx/store';
import { UserState }              from '@reducers/user.reducer';
import { Observable }             from 'rxjs';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
})
export class UserComponent implements OnInit {
  users$: Observable<UserInfo[]>;
  privileges$: Observable<Privilege[]>;
  selectedUser: UserInfo | undefined = undefined;

  constructor(private store: Store<{ user: UserState }>, private eventBusService: EventBusService) {
    this.users$ = store.select(state => state.user.users);
    this.privileges$ = store.select(state => state.user.privileges);
  }

  ngOnInit(): void {
    this.store.dispatch(loadUsers());
  }

  onSelectUser(user: UserInfo): void {
    this.selectedUser = user;
    this.eventBusService.emit(new EventData(EVENT_TYPES.USER_SELECTED, user));
  }
}
