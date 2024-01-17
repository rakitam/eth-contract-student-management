import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AuthService} from "../auth/auth.service";
import { catchError, map } from 'rxjs/operators';
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  private url = '/api/users'

  constructor(private authService: AuthService, private http: HttpClient) { }

  hasAnyRole(roles: string[]) {
    let userRole = this.authService.getUserRole();
    return roles.indexOf(userRole) !== -1;
  }

}
