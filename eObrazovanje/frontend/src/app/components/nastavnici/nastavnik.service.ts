import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Nastavnik} from "./nastavnik.model";

@Injectable({
  providedIn: 'root'
})
export class NastavnikService {

  private url = '/api/nastavnici'

  constructor(private httpClient: HttpClient) { }

  getAll({page = 0, size = 5, sortColumn = '',sortDirection = '', search= ''} = {page: 1, size: 5}): Observable<HttpResponse<Nastavnik[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search};
    return this.httpClient.get<Nastavnik[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<Nastavnik> {
    return this.httpClient.get<Nastavnik>(`${this.url}/${id}`);
  }

  add(nastavnik: Nastavnik): Observable<Nastavnik> {
    return this.httpClient.post<Nastavnik>(this.url, nastavnik);
  }

  edit(nastavnik: Nastavnik): Observable<Nastavnik> {
    return this.httpClient.put<Nastavnik>(`${this.url}/${nastavnik.id}`, nastavnik);
  }

  delete(id: number): Observable<Nastavnik> {
    return this.httpClient.delete<Nastavnik>(`${this.url}/${id}`);
  }

}
