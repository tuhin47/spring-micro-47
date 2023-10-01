import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as pako from 'pako';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {environment} from "@env/environment";

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    apiBase: string;
    debounceTime: number | undefined;

    constructor(
        private httpClient: HttpClient
    ) {
        this.apiBase = environment.API_URL;
    }

    get(url: string): Observable<any> {
      let apiURL: string;

      if (url.startsWith("chat")) {
        apiURL = url;
      } else {
        apiURL = this.apiBase + url;
      }
        return this.httpClient.get(apiURL).pipe(map(res => res));
    }

    postExport(url: string, param?: any): Observable<any> {
        return this.post(url,param,false,true)
    }

    post(url: string, param?: any, compress: boolean = false, isBlob: boolean = false): Observable<any> {
      let apiURL = this.apiBase + url;
      if (compress) {
        const headers: HttpHeaders = new HttpHeaders({
          'Content-Encoding': 'gzip',
          'Content-Type': 'application/octet-stream',
          'Access-Control-Allow-Origin': '*'
        });

        return this.httpClient.post(apiURL, pako.gzip(JSON.stringify(param)).buffer, {
          headers: headers,
          responseType: 'json'
        });
      } else if (isBlob) {
        return this.httpClient.post(
          apiURL, param, {
            responseType: 'blob',
            observe: 'response'
          }
        );
      } else {
        return this.httpClient.post(apiURL, param, {
          headers: new HttpHeaders({
            'Access-Control-Allow-Origin': '*'
          }),
        });
      }
    }

    put(url: string, param?: any): Observable<any> {
        let apiURL = this.apiBase + url;
        return this.httpClient.patch(apiURL, param).pipe(map(res => res));
    }

    delete(url: string): Observable<any> {
        let apiURL = this.apiBase + url;
        return this.httpClient.delete(apiURL).pipe(map(res => res));
    }

}
