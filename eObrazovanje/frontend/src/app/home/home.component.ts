import { Component } from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {UtilsService} from "../utils/utils.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent {

  constructor(private auth: AuthService, public utils: UtilsService) {
  }

  isAuthenticated() {
    return this.auth.isAuthenticated();
  }
}
