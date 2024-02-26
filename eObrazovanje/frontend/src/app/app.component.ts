import {Component, OnInit} from '@angular/core';
import {SpinnerService} from "./utils/spinner.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'eobrazovanje-frontend';
  isLoading: Observable<boolean> | undefined;

  constructor(private spinnerService: SpinnerService) {
  }

  ngOnInit() {
    this.isLoading = this.spinnerService.isLoading;
  }
}
