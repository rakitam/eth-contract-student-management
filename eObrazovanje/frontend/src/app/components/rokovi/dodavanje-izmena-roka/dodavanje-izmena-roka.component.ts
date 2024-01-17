import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";
import {Rok} from "../rok.model";
import {RokService} from "../rok.service";

@Component({
  selector: 'app-dodavanje-izmena-roka',
  templateUrl: './dodavanje-izmena-roka.component.html',
})
export class DodavanjeIzmenaRokaComponent {

  rok: Rok = {
    naziv: "",
    pocetak: "",
    kraj: "",
  }
  id: number = 0;

  constructor(private rokService: RokService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.rokService.get(this.id).subscribe(data => {
        this.rok = data;
      });
    }
  }

  save() {
    this.rok.pocetak = new Date(this.rok.pocetak);
    this.rok.kraj = new Date(this.rok.kraj);
    if (!isNaN(+this.rok.pocetak) && !isNaN(+this.rok.kraj)) {
      if (this.rok.pocetak.getTime() > this.rok.kraj.getTime() || this.rok.kraj.getTime() < this.rok.pocetak.getTime()) {
        alert("Početak roka ne može biti nakon njegovog kraja.");
        return;
      }
      if (this.id) {
        this.rokService.edit(this.rok).pipe(catchError(err => {
          // TODO handle error
          return err;
        })).subscribe(data => {
          this.router.navigate(['/rokovi'])
        });
        return;
      }
      this.rokService.add(this.rok).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/rokovi'])
      });
    }
  }
  get pageTitle(): string {
    return this.id ? 'Izmena roka' : 'Novi ispitni rok';
  }

}
