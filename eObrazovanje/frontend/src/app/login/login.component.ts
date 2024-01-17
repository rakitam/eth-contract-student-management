import {Component, OnInit} from '@angular/core';
import {Login} from "./login.model";
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import decode from "jwt-decode";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {

  user: Login = {
    username: '',
    password: ''
  }

  constructor(private auth: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    if (this.auth.isLoggedIn()) {
      this.router.navigate(['']);
    }
  }



  login() {
    this.auth.login(this.user);
  }

}
