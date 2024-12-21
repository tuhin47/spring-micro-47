// user.effects.ts
import {
  loadUserPrivileges,
  loadUserPrivilegesSuccess,
  loadUserRoles,
  loadUserRolesSuccess,
  loadUsers,
  loadUsersSuccess
}                                        from '@actions/user.actions';
import { Injectable }                    from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { UserService }                   from '@services/user.service';
import { of }                            from 'rxjs';
import { catchError, map, mergeMap }     from 'rxjs/operators';

@Injectable()
export class UserEffects {
  constructor(private actions$: Actions, private userService: UserService) {
  }

  loadUsers$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadUsers),
      mergeMap(() => this.userService.getUsers()
                         .pipe(
                           map(users => loadUsersSuccess({users})),
                           catchError(() => of({type: '[User] Load Users Failure'}))
                         ))
    )
  );

  loadRoles$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadUserRoles),
      mergeMap(action => this.userService.getRoles(action.userId)
                             .pipe(
                               map(roles => loadUserRolesSuccess({roles})),
                               catchError(() => of({type: '[User] Load Roles Failure'}))
                             ))
    )
  );

  loadPrivileges$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadUserPrivileges),
      mergeMap(action => this.userService.getPrivileges(action.userId)
                             .pipe(
                               map(privileges => loadUserPrivilegesSuccess({privileges})),
                               catchError(() => of({type: '[User] Load Privileges Failure'}))
                             ))
    )
  );
}