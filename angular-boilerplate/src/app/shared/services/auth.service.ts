import { HttpHeaders }    from '@angular/common/http';
import { Injectable }     from '@angular/core';
import { ApiService }     from '@services/api.service';
import { Observable, of } from 'rxjs';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: ApiService) {}

  register(displayName: string, email: string, password: string): Observable<any> {
    return this.http.post(
      'auth/signup',
      {
        displayName,
        email,
        password,
        matchingPassword : password,
        socialProvider: "LOCAL"
      }
    );
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post(
      'auth/signin',
      {
        email,
        password
      }
    );
  }

  logout(): Observable<any> {
    return of(null);
    // return this.http.post(AUTH_API + 'signout', { }, httpOptions);
  }

  /*refreshToken() {
    return this.http.post(AUTH_API + 'refreshtoken', { }, httpOptions);
  }*/
}
