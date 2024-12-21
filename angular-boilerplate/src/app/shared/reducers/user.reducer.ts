// user.reducer.ts
import { loadUserPrivilegesSuccess, loadUserRolesSuccess, loadUsersSuccess } from '@actions//user.actions';
import { Privilege }                                                         from '@models/privilege.model';
import { Role }                                                              from '@models/role.model';
import { UserInfo }                                                          from '@models/user.model';
import { createReducer, on }                                                 from '@ngrx/store';

export interface UserState {
  users: UserInfo[];
  roles: Role[];
  privileges: Privilege[];
}

export const initialState: UserState = {
  users: [],
  roles: [],
  privileges: []
};

export const userReducer = createReducer(
  initialState,
  on(loadUsersSuccess, (state, {users}) => ({...state, users})),
  on(loadUserRolesSuccess, (state, {roles}) => ({...state, roles})),
  on(loadUserPrivilegesSuccess, (state, {privileges}) => ({...state, privileges}))
);
