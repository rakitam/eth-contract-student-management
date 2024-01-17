import { Injectable } from '@angular/core';
import decode from 'jwt-decode';
import {Login} from "../login/login.model";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {Lozinka} from "./promena-lozinke/lozinka.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = '/api/login'
  private changePasswordUrl = '/api/promena-lozinke'

  constructor(private httpClient: HttpClient, private router: Router) { }

  public setToken(token: string) {
    localStorage.setItem('token', token);
  }

  public getToken(): string {
    return localStorage.getItem('token')!;
  }

  public isAuthenticated(): boolean {
    const currentTime = new Date().getTime();
    if (!this.getToken()) {
      return false;
    }
    let token: any = decode(this.getToken())
    return token.exp < currentTime;
  }

  removeToken() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

  login(user: Login){
    this.httpClient.post<any>(this.url, user, {observe: 'response'}).subscribe(data => {
      if (data.headers.has('Authorization')) {
        localStorage.setItem('token', data.headers.get('Authorization')!);
        localStorage.setItem('user', JSON.stringify(decode(data.headers.get('Authorization')!)));
        this.router.navigate(['/']);
      }
    }, error => {
      alert('Pogrešno korisničko ime ili lozinka.')
    });
  }

  getUserRole() {
    if (!this.getToken()) {
      return '';
    }
    let token: any = decode(this.getToken())
    return token.role;
  }

  getUsernameFromToken(): string {
    if (!this.getToken()) {
      return '';
    }
    let token: any = decode(this.getToken())
    return token.sub;
  }

  changePassword(lozinka: Lozinka): Observable<any> {
      return this.httpClient.put<any>(`${this.changePasswordUrl}`, lozinka);
  }

  isLoggedIn(): boolean {
    if (localStorage.getItem('token') && localStorage.getItem('user')) {
      return true;
    }
    return false;
  }

}
