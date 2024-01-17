import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class EthereumService {
  private url = '/api/ethereum';

  constructor(private http: HttpClient) {}

  triggerTransaction(data: any): Observable<string> {
    return this.http.post<string>(`${this.url}/trigger-transaction`, data);
  }
}
