import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from "./auth.service";
import {UtilsService} from "../utils/utils.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private auth: AuthService, private router: Router, private utils: UtilsService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot | any,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.auth.isLoggedIn()) {
      const retVal = this.utils.hasAnyRole(route.data.roles || []);
      if (!retVal) {
        this.router.navigate(['/'])
      }
      return retVal;
    } else {
      this.router.navigate(['/login'])
      return false;
    }
  }

}
