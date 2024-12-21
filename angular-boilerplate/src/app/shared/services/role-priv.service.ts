import { Injectable } from '@angular/core';
import { Privilege }  from '@models/privilege.model';
import { Role }       from '@models/role.model';
import { ApiService } from '@services/api.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RolePrivService {

  constructor(private apiService: ApiService) {
  }

  getRoles(): Observable<Role[]> {
    return this.apiService.get(`roles`);
  }

  getRoleById(roleId: number): Observable<Role[]> {
    return this.apiService.get(`roles/${roleId}`);
  }

  addRole(role: Role): Observable<Role> {
    return this.apiService.post(`roles/`, role);
  }

  editRole(role: Role): Observable<Role> {
    return this.apiService.put(`roles/`, role);
  }

  getPrivileges(): Observable<Privilege[]> {
    return this.apiService.get(`privileges`);
  }

  addPrivilege(priv: Privilege): Observable<Privilege> {
    return this.apiService.post(`privileges/`, priv);
  }

  editPrivilege(priv: Privilege): Observable<Privilege> {
    return this.apiService.put(`privileges/`, priv);
  }
}
