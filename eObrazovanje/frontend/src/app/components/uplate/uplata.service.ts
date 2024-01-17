import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Uplata} from "./uplata.model";
import {Dokument} from "../dokumenti/dokument.model";

@Injectable({
  providedIn: 'root'
})
export class UplataService {

  private url = '/api/uplate'

  constructor(private httpClient: HttpClient) { }

  getAll({page = 0, size = 5, sortColumn = '', sortDirection = '', search= '', studentUsername = ''} = {page: 1, size: 5}): Observable<HttpResponse<Uplata[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search, studentUsername};
    return this.httpClient.get<Uplata[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<Uplata> {
    return this.httpClient.get<Uplata>(`${this.url}/${id}`);
  }

  add(uplata: Uplata): Observable<Uplata> {
    return this.httpClient.post<Uplata>(this.url, uplata);
  }

  storniraj(id: number): Observable<Uplata> {
    return this.httpClient.post<Uplata>(`${this.url}/${id}/storniraj`, {});
  }

}
