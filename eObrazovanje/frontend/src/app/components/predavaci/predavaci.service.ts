import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Predaje} from "./predaje.model";
import {Student} from "../studenti/student.model";
import {Nastavnik} from "../nastavnici/nastavnik.model";

@Injectable({
  providedIn: 'root'
})
export class PredavaciService {

  private url = '/api/predaje'

  constructor(private httpClient: HttpClient) { }

  getAll({page = 0, size = 5, sortColumn = '',sortDirection = '', search= '', aktivniZaRok = false} = {page: 1, size: 5}): Observable<HttpResponse<Predaje[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search, aktivniZaRok};
    return this.httpClient.get<Predaje[]>(this.url, {observe: 'response', params: params});
  }

  getPredavaciForStudentPredmet(username: string): Observable<HttpResponse<Predaje[]>> {
    const params = { username };
    return this.httpClient.get<Predaje[]>(`${this.url}/predavaci/${username}`, {
      observe: 'response',
      params: params,
    });
  }

  get(id: number): Observable<Predaje> {
    return this.httpClient.get<Predaje>(`${this.url}/${id}`);
  }

  add(predaje: Predaje): Observable<Predaje> {
    return this.httpClient.post<Predaje>(this.url, predaje);
  }

  edit(predaje: Predaje): Observable<Predaje> {
    return this.httpClient.put<Predaje>(`${this.url}/${predaje.id}`, predaje);
  }

  delete(id: number): Observable<Predaje> {
    return this.httpClient.delete<Predaje>(`${this.url}/${id}`);
  }
}
