import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Zvanje} from "./zvanje.model";

@Injectable({
  providedIn: 'root'
})
export class ZvanjeService {

  private url = '/api/zvanja'

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<HttpResponse<Zvanje[]>> {
    return this.httpClient.get<Zvanje[]>(this.url, {observe: 'response'});
  }

  get(id: number): Observable<Zvanje> {
    return this.httpClient.get<Zvanje>(`${this.url}/${id}`);
  }

  add(zvanje: Zvanje): Observable<Zvanje> {
    return this.httpClient.post<Zvanje>(this.url, zvanje);
  }

  edit(zvanje: Zvanje): Observable<Zvanje> {
    return this.httpClient.put<Zvanje>(`${this.url}/${zvanje.id}`, zvanje);
  }

  delete(id: number): Observable<Zvanje> {
    return this.httpClient.delete<Zvanje>(`${this.url}/${id}`);
  }

}
