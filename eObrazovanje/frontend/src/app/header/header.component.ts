import {Component, DoCheck} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {UtilsService} from "../utils/utils.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent implements DoCheck {

  username: string;

  constructor(private auth: AuthService, private router: Router, public utils: UtilsService) {
    this.username = auth.getUsernameFromToken();
  }

  ngDoCheck(): void {
    this.username = this.auth.getUsernameFromToken();
  }

  logout() {
    this.auth.removeToken();
    this.router.navigate(['/login']);
  }

  isAuthenticated() {
    return this.auth.isAuthenticated();
  }
}
