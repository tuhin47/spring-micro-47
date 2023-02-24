import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {Endpoint} from "@enums/endpoint.enum";
import {ApiService} from "@services/api.service";


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
      Endpoint.SIGNUP,
      {
        displayName,
        email,
        password,
        matchingPassword : password,
        socialProvider: "LOCAL"
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
