import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {IspitniRok} from "./ispitni-rok.model";
import {Ispit} from "../ispiti/ispit.model";

@Injectable({
  providedIn: 'root'
})
export class IspitniRokService {

  private url = '/api/ispitni-rokovi'

  constructor(private httpClient: HttpClient) { }

  // getAll({page= 0, size= 5, student= ''}): Observable<HttpResponse<IspitniRok[]>> {
  //   const params = {page, size, student};
  //   return this.httpClient.get<IspitniRok[]>(this.url, {observe: 'response', params: params});
  // }

  getAll({page = 0, size = 5, sortColumn = '', sortDirection = '', search= '', student = ''} = {page: 1, size: 5}): Observable<HttpResponse<IspitniRok[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search, student};
    return this.httpClient.get<IspitniRok[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<IspitniRok> {
    return this.httpClient.get<IspitniRok>(`${this.url}/${id}`);
  }

  getAktivniRok(): Observable<IspitniRok> {
    return this.httpClient.get<IspitniRok>(`${this.url}/aktivni/rok`);
  }

  add(ispitniRok: IspitniRok): Observable<IspitniRok> {
    return this.httpClient.post<IspitniRok>(this.url, ispitniRok);
  }

  edit(ispitniRok: IspitniRok): Observable<IspitniRok> {
    return this.httpClient.put<IspitniRok>(`${this.url}/${ispitniRok.id}`, ispitniRok);
  }

  delete(id: number): Observable<IspitniRok> {
    return this.httpClient.delete<IspitniRok>(`${this.url}/${id}`);
  }

}
