import {Component, OnInit} from '@angular/core';
import {Predmet} from "../predmet.model";
import {PredmetService} from "../predmet.service";
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";

@Component({
  selector: 'app-dodavanje-izmena-predmet',
  templateUrl: './dodavanje-izmena-predmet.component.html',
})
export class DodavanjeIzmenaPredmetComponent implements OnInit {

  predmet: Predmet = {
    naziv: '',
    oznaka: '',
    espb: 0,
  }
  id: number = 0;

  constructor(private predmetService: PredmetService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.predmetService.get(this.id).subscribe(data => {
        this.predmet = data;
      });
    }
  }

  save() {
    if (this.id) {
      this.predmetService.edit(this.predmet).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/predmeti'])
      });
      return;
    }
    this.predmetService.add(this.predmet).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/predmeti'])
    });
  }

  get pageTitle(): string {
    return this.id ? 'Izmena predmeta' : 'Dodavanje predmeta';
  }

}
