import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Nastavnik} from "../nastavnik.model";
import {NastavnikService} from "../nastavnik.service";
import {UtilsService} from "../../../utils/utils.service";

@Component({
  selector: 'app-dodavanje-izmena-nastavnik',
  templateUrl: './dodavanje-izmena-nastavnik.component.html',
})
export class DodavanjeIzmenaNastavnikComponent {

  nastavnik: Nastavnik = {
    ime: '',
    prezime: '',
    jmbg: '',
    adresa: '',
    username: ''
  }
  id: number = 0;
  isDisabled = false;

  constructor(private nastavnikService: NastavnikService, private utilsService: UtilsService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.isDisabled = true;
      this.nastavnikService.get(this.id).subscribe(data => {
        this.nastavnik = data;
      });
    }
  }

  save() {
    // Check if a username is entered
    if (!this.nastavnik?.username) {
      alert('Morate uneti korisničko ime.');
      return;
    }
    // Add or edit the professor as necessary
    if (this.id) {
      this.nastavnikService.edit(this.nastavnik).subscribe(() => {
        this.router.navigate(['/nastavnici']);
      }, error => {
        alert(error.error);
      });
    } else {
      this.nastavnikService.add(this.nastavnik).subscribe(() => {
        this.router.navigate(['/nastavnici']);
      }, error => {
        alert(error.error);
      });
    }
  }

  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }

  get pageTitle(): string {
    return this.id ? 'Izmena nastavnika' : 'Dodavanje nastavnika';
  }

}
