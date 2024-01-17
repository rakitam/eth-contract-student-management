import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Prijava} from "./prijava.model";
import {Nastavnik} from "../nastavnici/nastavnik.model";
import {Student} from "../studenti/student.model";
import {Dokument} from "../dokumenti/dokument.model";

@Injectable({
  providedIn: 'root'
})
export class PrijavaService {

  private url = '/api/prijave'

  constructor(private httpClient: HttpClient) { }

  getAll({page = 0, size = 5, sortColumn = '', sortDirection = '', search= '', studentUsername = ''} = {page: 1, size: 5}): Observable<HttpResponse<Prijava[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search, studentUsername};
    return this.httpClient.get<Prijava[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<Prijava> {
    return this.httpClient.get<Prijava>(`${this.url}/${id}`);
  }

  add(prijava: Prijava): Observable<Prijava> {
    return this.httpClient.post<Prijava>(this.url, prijava);
  }

  edit(prijava: Prijava): Observable<Prijava> {
    return this.httpClient.put<Prijava>(`${this.url}/${prijava.id}`, prijava);
  }

  delete(id: number): Observable<Prijava> {
    return this.httpClient.delete<Prijava>(`${this.url}/${id}`);
  }

}
