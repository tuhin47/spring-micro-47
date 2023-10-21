import { Injectable } from '@angular/core';
import { ApiService } from '@services/api.service';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private apiService: ApiService) { }

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

  getUsers() {
    return this.apiService.get('auth/users/summaries');
  }

  getFiles() {
    return this.apiService.get('auth/menu');
  }
}
