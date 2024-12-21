import { Injectable } from '@angular/core';
import { Privilege }  from '@models/privilege.model';
import { Role }       from '@models/role.model';
import { UserInfo }   from '@models/user.model';
import { ApiService } from '@services/api.service';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private apiService: ApiService) {
  }

  getPublicContent(): Observable<any> {
    return this.apiService.get('all');
  }

  getUserBoard(): Observable<any> {
    return this.apiService.get('user');
  }

  getModeratorBoard(): Observable<any> {
    return this.apiService.get('mod');
  }

  getAdminBoard(): Observable<any> {
    return this.apiService.get('admin');
  }

  getCurrentUser(): Observable<any> {
    return this.apiService.get('auth/user/me');
  }

  getFiles() {
    return this.apiService.get('auth/menu');
  }

  getUsers(): Observable<UserInfo[]> {
    return this.apiService.get('auth/users');
  }

  getRoles(userId: string): Observable<Role[]> {
    return this.apiService.get(`roles/users/${userId}`);
  }

  getPrivileges(userId: string): Observable<Privilege[]> {
    return this.apiService.get(`privileges/users/${userId}`);
  }
}
