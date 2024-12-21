import { addRole, addRoleSuccess, editRole, editRoleSuccess, loadRoles, loadRolesSuccess } from '@actions/role.actions';
import { Injectable }                                                                      from '@angular/core';
import { Actions, createEffect, ofType }                                                   from '@ngrx/effects';
import {
  RolePrivService
}                                                                                          from '@services/role-priv.service';
import { of }                                                                              from 'rxjs';
import { catchError, map, mergeMap }                                                       from 'rxjs/operators';

@Injectable()
export class RoleEffects {
  constructor(private actions$: Actions, private rolePrivService: RolePrivService) {
  }

  loadRoles$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadRoles),
      mergeMap(() => this.rolePrivService.getRoles()
                         .pipe(
                           map(roles => loadRolesSuccess({roles})),
                           catchError(() => of({type: '[Role] Load Roles Failure'}))
                         ))
    )
  );

  addRole$ = createEffect(() =>
    this.actions$.pipe(
      ofType(addRole),
      mergeMap(action => this.rolePrivService.addRole(action.role)
                             .pipe(
                               map(role => addRoleSuccess({role})),
                               catchError(() => of({type: '[Role] Add Role Failure'}))
                             ))
    )
  );

  editRole$ = createEffect(() =>
    this.actions$.pipe(
      ofType(editRole),
      mergeMap(action => this.rolePrivService.editRole(action.role)
                             .pipe(
                               map(role => editRoleSuccess({role})),
                               catchError(() => of({type: '[Role] Edit Role Failure'}))
                             ))
    )
  );
}
