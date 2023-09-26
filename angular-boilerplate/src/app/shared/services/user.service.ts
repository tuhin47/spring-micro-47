import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ApiService} from "@services/api.service";
import {Endpoint} from "@enums/endpoint.enum";


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
      return this.apiService.get(Endpoint.MY_INFO);
  }

  getUsers() {
    return this.apiService.get(Endpoint.FRIEND_LIST);
  }

  getFiles() {
    return this.apiService.get(Endpoint.MENU_LIST);
  }
}
