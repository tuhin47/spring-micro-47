// user.actions.ts
import { Privilege }           from '@models/privilege.model';
import { Role }                from '@models/role.model';
import { UserInfo }            from '@models/user.model';
import { createAction, props } from '@ngrx/store';


export const loadUsers = createAction('[User] Load Users');
export const loadUsersSuccess = createAction('[User] Load Users Success', props<{ users: UserInfo[] }>());
export const loadUserRoles = createAction('[User] Load Roles', props<{ userId: string }>());
export const loadUserRolesSuccess = createAction('[User] Load Roles Success', props<{ roles: Role[] }>());
export const loadUserPrivileges = createAction('[User] Load Privileges', props<{ userId: string }>());
export const loadUserPrivilegesSuccess = createAction('[User] Load Privileges Success', props<{
  privileges: Privilege[]
}>());