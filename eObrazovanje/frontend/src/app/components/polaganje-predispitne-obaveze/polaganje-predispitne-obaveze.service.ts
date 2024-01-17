import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {PolaganjePredispitneObaveze} from "./polaganje-predispitne-obaveze.model";

@Injectable({
  providedIn: 'root'
})
export class PolaganjePredispitneObavezeService {

  private url = '/api/polaganje-predispitne-obaveze'

  constructor(private httpClient: HttpClient) { }

  getAll(queryParams = {page: 1, size: 5, studentUsername: ''}): Observable<HttpResponse<PolaganjePredispitneObaveze[]>> {
    return this.httpClient.get<PolaganjePredispitneObaveze[]>(this.url, {observe: 'response', params: queryParams});
  }

  get(id: number): Observable<PolaganjePredispitneObaveze> {
    return this.httpClient.get<PolaganjePredispitneObaveze>(`${this.url}/${id}`);
  }

  add(polaganjePredispitneObaveze: PolaganjePredispitneObaveze): Observable<PolaganjePredispitneObaveze> {
    return this.httpClient.post<PolaganjePredispitneObaveze>(this.url, polaganjePredispitneObaveze);
  }

  edit(polaganjePredispitneObaveze: PolaganjePredispitneObaveze): Observable<PolaganjePredispitneObaveze> {
    return this.httpClient.put<PolaganjePredispitneObaveze>(`${this.url}/${polaganjePredispitneObaveze.id}`, polaganjePredispitneObaveze);
  }

  delete(id: number): Observable<PolaganjePredispitneObaveze> {
    return this.httpClient.delete<PolaganjePredispitneObaveze>(`${this.url}/${id}`);
  }

}
