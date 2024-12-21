import { addRoleSuccess, editRoleSuccess, loadRolesSuccess } from '@actions/role.actions';
import { Role }                                              from '@models/role.model';
import { createReducer, on }                                 from '@ngrx/store';

export interface RoleState {
  roles: Role[];
}

export const initialState: RoleState = {
  roles: []
};

export const roleReducer = createReducer(
  initialState,
  on(loadRolesSuccess, (state, {roles}) => ({...state, roles})),
  on(addRoleSuccess, (state, {role}) => ({...state, roles: [...state.roles, role]})),
  on(editRoleSuccess, (state, {role}) => ({
    ...state,
    roles: state.roles.map(r => r.id === role.id ? role : r)
  }))
);
