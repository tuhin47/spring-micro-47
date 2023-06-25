import {Injectable} from '@angular/core';
import {ApiService} from "@services/api.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private apiService: ApiService) {
  }

  getExcelData(): Observable<any> {
    return this.apiService.postExport('product/excel?all=true');
  }
}
