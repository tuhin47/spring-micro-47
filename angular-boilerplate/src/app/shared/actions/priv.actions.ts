import { Privilege }           from '@models/privilege.model';
import { createAction, props } from '@ngrx/store';

export const loadPrivileges = createAction('[Privilege] Load Privileges');
export const loadPrivilegesSuccess = createAction('[Privilege] Load Privileges Success', props<{
  privileges: Privilege[]
}>());
export const addPrivilege = createAction('[Privilege] Add Privilege', props<{ privilege: Privilege }>());
export const addPrivilegeSuccess = createAction('[Privilege] Add Privilege Success', props<{ privilege: Privilege }>());
export const editPrivilege = createAction('[Privilege] Edit Privilege', props<{ privilege: Privilege }>());
export const editPrivilegeSuccess = createAction('[Privilege] Edit Privilege Success', props<{
  privilege: Privilege
}>());
