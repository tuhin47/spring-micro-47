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
    return this.apiService.postExport('product/all/excel?size=1&page=0&sort=productName,desc;productId,asc',
      [
        {
          key: "productName",
          operation: "MATCH",
          value: "Pro"
        }
      ]);
  }
}
