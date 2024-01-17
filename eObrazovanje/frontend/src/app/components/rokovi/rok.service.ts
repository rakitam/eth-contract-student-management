import { Injectable } from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from "@angular/common/http";
import {catchError, Observable, of} from "rxjs";
import {Rok} from "./rok.model";
import {map} from "rxjs/operators";
import {Dokument} from "../dokumenti/dokument.model";

@Injectable({
  providedIn: 'root'
})
export class RokService {

  private url = '/api/rokovi'

  constructor(private httpClient: HttpClient) { }

  getAll({page = 0, size = 5, sortColumn = '', sortDirection = '', search= ''} = {page: 1, size: 5}): Observable<HttpResponse<Rok[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search};
    return this.httpClient.get<Rok[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<Rok> {
    return this.httpClient.get<Rok>(`${this.url}/${id}`);
  }

  getAktivniRok(): Observable<Rok> {
    return this.httpClient.get<Rok>(`${this.url}/aktivni/rok`);
  }

  add(rok: Rok): Observable<Rok> {
    return this.httpClient.post<Rok>(this.url, rok);
  }

  edit(rok: Rok): Observable<Rok> {
    return this.httpClient.put<Rok>(`${this.url}/${rok.id}`, rok);
  }

  delete(id: number): Observable<Rok> {
    return this.httpClient.delete<Rok>(`${this.url}/${id}`);
  }

}
