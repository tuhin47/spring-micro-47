import { addRole, editRole } from '@actions/role.actions';
import { Component }         from '@angular/core';
import { NgForm }            from '@angular/forms';
import { EventBusService }   from '@event/event-bus.service';
import { EVENT_TYPES }       from '@event/event.class';
import { Role }              from '@models/role.model';
import { Store }             from '@ngrx/store';

@Component({
  selector: 'app-role-edit',
  templateUrl: './role-edit.component.html',
})
export class RoleEditComponent {
  role?: Role;

  constructor(private store: Store, private eventBusService: EventBusService) {
    this.eventBusService.on(EVENT_TYPES.EDIT_ROLE, (data: Role) => {
      this.role = data;
    });
    this.eventBusService.on(EVENT_TYPES.ADD_ROLE, (data: any) => {
      this.role = new Role(0, '', []);
    });
  }

  onSubmit(roleForm: NgForm) {
    if (roleForm.invalid || !this.role) {
      return;
    }
    if (this.role.id) {
      this.store.dispatch(editRole({role: this.role}));
    } else {
      this.store.dispatch(addRole({role: this.role}));
    }
  }
}
