import {
  addPrivilege,
  addPrivilegeSuccess,
  editPrivilege,
  editPrivilegeSuccess,
  loadPrivileges,
  loadPrivilegesSuccess
}                                        from '@actions/priv.actions';
import { Injectable }                    from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { RolePrivService }               from '@services/role-priv.service';
import { of }                            from 'rxjs';
import { catchError, map, mergeMap }     from 'rxjs/operators';

@Injectable()
export class PrivilegeEffects {
  constructor(private actions$: Actions, private privilegeService: RolePrivService) {
  }

  loadPrivileges$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadPrivileges),
      mergeMap(() => this.privilegeService.getPrivileges()
                         .pipe(
                           map(privileges => loadPrivilegesSuccess({privileges})),
                           catchError(() => of({type: '[Privilege] Load Privileges Failure'}))
                         ))
    )
  );

  addPrivilege$ = createEffect(() =>
    this.actions$.pipe(
      ofType(addPrivilege),
      mergeMap(action => this.privilegeService.addPrivilege(action.privilege)
                             .pipe(
                               map(privilege => addPrivilegeSuccess({privilege})),
                               catchError(() => of({type: '[Privilege] Add Privilege Failure'}))
                             ))
    )
  );

  editPrivilege$ = createEffect(() =>
    this.actions$.pipe(
      ofType(editPrivilege),
      mergeMap(action => this.privilegeService.editPrivilege(action.privilege)
                             .pipe(
                               map(privilege => editPrivilegeSuccess({privilege})),
                               catchError(() => of({type: '[Privilege] Edit Privilege Failure'}))
                             ))
    )
  );
}
