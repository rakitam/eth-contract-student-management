import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";
import {IspitniRok} from "../ispitni-rok.model";
import {IspitniRokService} from "../ispitni-rok.service";
import {Rok} from "../../rokovi/rok.model";
import {RokService} from "../../rokovi/rok.service";
import {PredavaciService} from "../../predavaci/predavaci.service";
import {Predaje} from "../../predavaci/predaje.model";

@Component({
  selector: 'app-dodavanje-izmena-ispitnog-roka',
  templateUrl: './dodavanje-izmena-ispitnog-roka.component.html',
})
export class DodavanjeIzmenaIspitnogRokaComponent {

  ispitniRok: IspitniRok = {
    predaje: null,
    rok: null,
    datumIspita: ''
  }
  id: number = 0;

  predavaci: Predaje[] = [];
  rokovi: Rok[] = [];

  constructor(private ispitniRokService: IspitniRokService, private router: Router, private route: ActivatedRoute,
              private predajeService: PredavaciService, private rokService: RokService) {}

  private ucitvanjePredavaca() {
    this.predajeService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.predavaci = response.body!;
    });
  }

  private ucitvanjeRokova() {
    this.rokService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.rokovi = response.body!;
    });
  }

  ngOnInit(): void {
    this.ucitvanjePredavaca();
    this.ucitvanjeRokova();
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.ispitniRokService.get(this.id).subscribe(data => {
        this.ispitniRok = data;
      });
    }
  }

  save() {
    if (this.id) {
      this.ispitniRokService.edit(this.ispitniRok).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/ispitni-rokovi'])
      });
      return;
    }
    this.ispitniRokService.add(this.ispitniRok).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/ispitni-rokovi'])
    });
  }

  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }

  get pageTitle(): string {
    return this.id ? 'Izmena ispita u roku' : 'Dodavanje ispita u roku';
  }
}
