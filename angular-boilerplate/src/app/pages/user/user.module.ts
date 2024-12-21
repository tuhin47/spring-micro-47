import { CommonModule }                from '@angular/common';
import { NgModule }                    from '@angular/core';
import { PrivilegeEffects }            from '@effects/priv.effects';
import { RoleEffects }                 from '@effects/role.effects';
import { EffectsModule }               from '@ngrx/effects';
import { StoreModule }                 from '@ngrx/store';
import { privilegeReducer }            from '@reducers/priv.reducer';
import { roleReducer }                 from '@reducers/role.reducer';
import { CheckboxModule }              from 'primeng/checkbox';
import { ListboxModule }               from 'primeng/listbox';
import { SharedModule }                from '../../shared/shared.module';
import { PrivilegeChecklistComponent } from './priv/privilege-checklist/privilege-checklist.component';
import { RoleChecklistComponent }      from './role/role-checklist/role-checklist.component';
import { RoleEditComponent }           from './role/role-edit/role-edit.component';
import { RoleListComponent }           from './role/role-list/role-list.component';
import { RoleComponent }               from './role/role.component';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent }     from './user/user.component';


@NgModule({
  declarations: [
    UserComponent,
    RoleComponent,
    RoleListComponent,
    RoleEditComponent,
    RoleChecklistComponent,
    PrivilegeChecklistComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    SharedModule,
    StoreModule.forFeature('role', roleReducer),
    StoreModule.forFeature('priv', privilegeReducer),
    EffectsModule.forFeature([RoleEffects, PrivilegeEffects]),
    ListboxModule,
    CheckboxModule
  ]
})
export class UserModule {
}
