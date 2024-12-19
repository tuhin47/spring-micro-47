import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  constructor(private http: HttpClient) {
  }

  saveMenu(menuData: any): Observable<any> {
    return this.http.post<any>(`api/auth/menu`, menuData);
  }

  getMenus(): Observable<any[]> {
    return this.http.get<any[]>('api/auth/menu/options');
  }

  deleteMenu(id: number): Observable<any> {
    return this.http.delete<any>(`api/menus/${id}`);
  }

  getMenuById(id: number) {
    return this.http.get<any[]>(`api/auth/menu/${id}`);
  }
}
