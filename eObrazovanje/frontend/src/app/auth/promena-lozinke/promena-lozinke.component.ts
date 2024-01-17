import { Component } from '@angular/core';
import {Lozinka} from "./lozinka.model";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-promena-lozinke',
  templateUrl: './promena-lozinke.component.html',
})
export class PromenaLozinkeComponent {

  lozinka: Lozinka = {
    password: '',
    repeatPassword: '',
  }

  constructor(private auth: AuthService, private router: Router) {}

  promeni() {
    if (this.lozinka.password === this.lozinka.repeatPassword) {
      this.auth.changePassword(this.lozinka).subscribe(data => {
        this.router.navigate(['/'])
      });
    }
  }

}
