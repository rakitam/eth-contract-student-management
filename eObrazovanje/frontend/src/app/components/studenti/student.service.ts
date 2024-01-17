import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Student} from "./student.model";
import {Predmet} from "../predmet/predmet.model";
import {catchError, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private url = '/api/studenti'

  constructor(private httpClient: HttpClient) { }

  getAll({page= 1, size= 5, sortColumn = '',sortDirection = '', search= ''}): Observable<HttpResponse<Student[]>> {
    const params = {page, size, sort: `${sortColumn},${sortDirection}`, search}
    return this.httpClient.get<Student[]>(this.url, {observe: 'response', params: params});
  }

  get(username: string): Observable<Student> {
    return this.httpClient.get<Student>(`${this.url}/${username}`);
  }

  add(student: Student): Observable<Student> {
    return this.httpClient.post<Student>(this.url, student);
  }

  edit(student: Student): Observable<Student> {
    return this.httpClient.put<Student>(`${this.url}/${student.id}`, student);
  }

  delete(id: number): Observable<Student> {
    return this.httpClient.delete<Student>(`${this.url}/${id}`);
  }

}
