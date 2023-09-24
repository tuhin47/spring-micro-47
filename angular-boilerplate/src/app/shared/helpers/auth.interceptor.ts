import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpStatusCode
}                          from '@angular/common/http';
import { Injectable }      from '@angular/core';
import { Router }          from '@angular/router';
import { StorageHelper }   from '@helpers/storage.helper';
import { Observable }      from 'rxjs';
import { tap }             from 'rxjs/operators';
import { EventBusService } from '../event/event-bus.service';
import { EventData }       from '../event/event.class';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private router: Router, private eventBusService: EventBusService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    const loginPath = '/login';
    const token = StorageHelper.getToken();
    if (token != null) {
      authReq = req.clone({headers: req.headers.set('Authorization', `Bearer ${token}`)});
    }
    return next.handle(authReq).pipe(tap(() => {
      },
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== HttpStatusCode.Unauthorized || window.location.pathname === loginPath) {
            return;
          }
          this.eventBusService.emit(new EventData('logout', null));
        }
      }
    ));
  }
}

export const authInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
];
