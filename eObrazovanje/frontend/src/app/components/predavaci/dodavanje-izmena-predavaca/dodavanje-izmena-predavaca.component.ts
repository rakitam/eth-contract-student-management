import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";
import {Predaje} from "../predaje.model";
import {PredavaciService} from "../predavaci.service";
import {Nastavnik} from "../../nastavnici/nastavnik.model";
import {Predmet} from "../../predmet/predmet.model";
import {Zvanje} from "../../zvanja/zvanje.model";
import {PredmetService} from "../../predmet/predmet.service";
import {NastavnikService} from "../../nastavnici/nastavnik.service";
import {ZvanjeService} from "../../zvanja/zvanje.service";

@Component({
  selector: 'app-dodavanje-izmena-predavaca',
  templateUrl: './dodavanje-izmena-predavaca.component.html',
  styles: [
  ]
})
export class DodavanjeIzmenaPredavacaComponent {

  predavac: Predaje = {
    predmet: null,
    zvanje: null,
    nastavnik: null
  }
  id: number = 0;
  isDisabled = false;

  nastavnici: Nastavnik[] = [];
  predmeti: Predmet[] = [];
  zvanja: Zvanje[] = [];
  predavaci: Predaje[] = [];

  constructor(private predavaciService: PredavaciService, private router: Router, private route: ActivatedRoute,
              private predmetService: PredmetService, private nastavnikService: NastavnikService, private zvanjeService: ZvanjeService) {}

  private ucitavanjePredmeta() {
    this.predmetService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.predmeti = response.body!;
    });
  }

  private ucitavanjeZvanja() {
    this.zvanjeService.getAll().subscribe(response => {
      this.zvanja = response.body!;
    });
  }

  private ucitavanjeNastavnika() {
    this.nastavnikService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.nastavnici = response.body!;
    });
  }

  private ucitavanjePredavaca() {
    this.predavaciService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.predavaci = response.body!;
    });
  }

  ngOnInit(): void {
    this.ucitavanjeNastavnika();
    this.ucitavanjePredmeta();
    this.ucitavanjeZvanja();
    this.ucitavanjePredavaca();
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.isDisabled = true;
      this.predavaciService.get(this.id).subscribe(data => {
        this.predavac = data;
      });
    }
  }

  save() {
    // Check if a lecturer with the same role and subject already exists
    const existingLecturer = this.predavaci.find(predavac =>
      predavac.predmet?.id === this.predavac.predmet?.id &&
      predavac.nastavnik?.id === this.predavac.nastavnik?.id &&
      predavac.zvanje?.id === this.predavac.zvanje?.id
    );

    if (existingLecturer) {
      alert('Predavač sa istom ulogom već postoji za ovaj predmet.');
      return;
    }

    // Save the new lecturer
    if (this.id) {
      this.predavaciService.edit(this.predavac).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/predavaci'])
      });
      return;
    }
    this.predavaciService.add(this.predavac).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/predavaci'])
    });
  }

  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }

  get pageTitle(): string {
    return this.id ? 'Izmena predavača' : 'Dodavanje predavača';
  }
}
