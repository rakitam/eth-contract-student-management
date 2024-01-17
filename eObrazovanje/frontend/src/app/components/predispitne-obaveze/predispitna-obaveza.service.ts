import { Injectable } from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {PredispitnaObaveza} from "./predispitna-obaveza.model";

@Injectable({
  providedIn: 'root'
})
export class PredispitnaObavezaService {

  private url = '/api/predispitne-obaveze'

  constructor(private httpClient: HttpClient) { }

  getAll(predmetId?: any): Observable<HttpResponse<PredispitnaObaveza[]>> {
    let params = new HttpParams();
    if (predmetId) {
      params.append('predmetId', predmetId);
    }
    return this.httpClient.get<PredispitnaObaveza[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<PredispitnaObaveza> {
    return this.httpClient.get<PredispitnaObaveza>(`${this.url}/${id}`);
  }

  add(predispitnaObaveza: PredispitnaObaveza): Observable<PredispitnaObaveza> {
    return this.httpClient.post<PredispitnaObaveza>(this.url, predispitnaObaveza);
  }

  edit(predispitnaObaveza: PredispitnaObaveza): Observable<PredispitnaObaveza> {
    return this.httpClient.put<PredispitnaObaveza>(`${this.url}/${predispitnaObaveza.id}`, predispitnaObaveza);
  }

  delete(id: number): Observable<PredispitnaObaveza> {
    return this.httpClient.delete<PredispitnaObaveza>(`${this.url}/${id}`);
  }

}
