import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Predmet} from "./predmet.model";
import {Nastavnik} from "../nastavnici/nastavnik.model";
import {Uplata} from "../uplate/uplata.model";

@Injectable({
  providedIn: 'root'
})
export class PredmetService {

  private url = '/api/predmeti'

  constructor(private httpClient: HttpClient) { }

  getAll({page = 0, size = 5, sortColumn = '', sortDirection = '', search= '', studentUsername = ''} = {page: 1, size: 5}): Observable<HttpResponse<Predmet[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search};
    return this.httpClient.get<Predmet[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<Predmet> {
    return this.httpClient.get<Predmet>(`${this.url}/${id}`);
  }

  add(predmet: Predmet): Observable<Predmet> {
    return this.httpClient.post<Predmet>(this.url, predmet);
  }

  edit(predmet: Predmet): Observable<Predmet> {
    return this.httpClient.put<Predmet>(`${this.url}/${predmet.id}`, predmet);
  }

  delete(id: number): Observable<Predmet> {
    return this.httpClient.delete<Predmet>(`${this.url}/${id}`);
  }

}
