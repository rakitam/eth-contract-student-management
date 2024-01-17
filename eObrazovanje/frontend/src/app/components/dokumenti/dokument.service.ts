import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Dokument} from "./dokument.model";
import {PolaganjePredispitneObaveze} from "../polaganje-predispitne-obaveze/polaganje-predispitne-obaveze.model";
import {Nastavnik} from "../nastavnici/nastavnik.model";

@Injectable({
  providedIn: 'root'
})
export class DokumentService {

  private url = '/api/dokumenti'

  constructor(private httpClient: HttpClient) { }

  getAll({page = 0, size = 5, sortColumn = '', sortDirection = '', search= '', studentUsername = ''} = {page: 1, size: 5}): Observable<HttpResponse<Dokument[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search, studentUsername};
    return this.httpClient.get<Dokument[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<Dokument> {
    return this.httpClient.get<Dokument>(`${this.url}/${id}`);
  }

  download(id: number) {
    this.httpClient.get(`${this.url}/${id}/download`, {responseType: 'blob'}).subscribe(data => {
      window.open(window.URL.createObjectURL(data));
    });
  }

  add(dokument: Dokument, file: any): Observable<Dokument> {
    let fd = new FormData();
    fd.append('file', file);
    fd.append('dokument', new Blob([JSON.stringify(dokument)], {type: 'application/json'}));
    return this.httpClient.post<Dokument>(this.url, fd);
  }

  edit(dokument: Dokument, file: any): Observable<Dokument> {
    let fd = new FormData();
    if (file) {
      fd.append('file', file);
    }
    fd.append('dokument', new Blob([JSON.stringify(dokument)], {type: 'application/json'}));
    return this.httpClient.put<Dokument>(`${this.url}/${dokument.id}`, fd);
  }

  delete(id: number): Observable<Dokument> {
    return this.httpClient.delete<Dokument>(`${this.url}/${id}`);
  }

}
