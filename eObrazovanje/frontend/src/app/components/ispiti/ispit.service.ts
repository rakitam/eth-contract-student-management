import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Ispit} from "./ispit.model";

@Injectable({
  providedIn: 'root'
})
export class IspitService {

  private url = '/api/ispiti'

  constructor(private httpClient: HttpClient) { }

  getAll({page = 0, size = 5, sortColumn = '', sortDirection = '', search= '', studentUsername = ''} = {page: 1, size: 5}): Observable<HttpResponse<Ispit[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search, studentUsername};
    return this.httpClient.get<Ispit[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<Ispit> {
    return this.httpClient.get<Ispit>(`${this.url}/${id}`);
  }

  getOneByUsernameAndId(username: string, id: number): Observable<Ispit> {
    const url = `${this.url}/profile/${username}/ispiti/${id}`;
    return this.httpClient.get<Ispit>(url);
  }


  add(ispit: Ispit): Observable<Ispit> {
    return this.httpClient.post<Ispit>(this.url, ispit);
  }

  edit(ispit: Ispit): Observable<Ispit> {
    return this.httpClient.put<Ispit>(`${this.url}/${ispit.id}`, ispit);
  }

}
