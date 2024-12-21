import { Role }                from '@models/role.model';
import { createAction, props } from '@ngrx/store';

export const loadRoles = createAction('[Role] Load Roles');
export const loadRolesSuccess = createAction('[Role] Load Roles Success', props<{ roles: Role[] }>());
export const addRole = createAction('[Role] Add Role', props<{ role: Role }>());
export const addRoleSuccess = createAction('[Role] Add Role Success', props<{ role: Role }>());
export const editRole = createAction('[Role] Edit Role', props<{ role: Role }>());
export const editRoleSuccess = createAction('[Role] Edit Role Success', props<{ role: Role }>());
