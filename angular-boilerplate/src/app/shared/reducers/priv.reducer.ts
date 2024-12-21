import { addPrivilegeSuccess, editPrivilegeSuccess, loadPrivilegesSuccess } from '@actions/priv.actions';
import { Privilege }                                                        from '@models/privilege.model';
import { createReducer, on }                                                from '@ngrx/store';

export interface PrivilegeState {
  privileges: Privilege[];
}

export const initialState: PrivilegeState = {
  privileges: []
};

export const privilegeReducer = createReducer(
  initialState,
  on(loadPrivilegesSuccess, (state, {privileges}) => ({...state, privileges})),
  on(addPrivilegeSuccess, (state, {privilege}) => ({...state, privileges: [...state.privileges, privilege]})),
  on(editPrivilegeSuccess, (state, {privilege}) => ({
    ...state,
    privileges: state.privileges.map(p => p.id === privilege.id ? privilege : p)
  }))
);
