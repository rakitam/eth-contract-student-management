import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {StudentPredmet} from "./student-predmet.model";

@Injectable({
  providedIn: 'root'
})
export class StudentPredmetService {

  private url = '/api/student-predmet'

  constructor(private httpClient: HttpClient) { }

  getAll({page = 0, size = 5, sortColumn = '',sortDirection = '', search= '', minBodova = 0, maxBodova = 100, predmetId = 0} = {page: 1, size: 5}): Observable<HttpResponse<StudentPredmet[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search, minBodova, maxBodova, predmetId};
    return this.httpClient.get<StudentPredmet[]>(this.url, {observe: 'response', params: params});
  }

  get(id: number): Observable<StudentPredmet> {
    return this.httpClient.get<StudentPredmet>(`${this.url}/${id}`);
  }

  add(studentPredmet: StudentPredmet): Observable<StudentPredmet> {
    return this.httpClient.post<StudentPredmet>(this.url, studentPredmet);
  }

  edit(studentPredmet: StudentPredmet): Observable<StudentPredmet> {
    return this.httpClient.put<StudentPredmet>(`${this.url}/${studentPredmet.id}`, studentPredmet);
  }

  delete(id: number): Observable<StudentPredmet> {
    return this.httpClient.delete<StudentPredmet>(`${this.url}/${id}`);
  }

}
