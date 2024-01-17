import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";
import {ZvanjeService} from "../zvanje.service";
import {Zvanje} from "../zvanje.model";

@Component({
  selector: 'app-dodavanje-izmena-zvanje',
  templateUrl: './dodavanje-izmena-zvanje.component.html',
})
export class DodavanjeIzmenaZvanjeComponent {


  zvanje: Zvanje = {
    naziv: '',
  }
  id: number = 0;

  constructor(private zvanjeService: ZvanjeService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.zvanjeService.get(this.id).subscribe(data => {
        this.zvanje = data;
      });
    }
  }

  save() {
    if (this.id) {
      this.zvanjeService.edit(this.zvanje).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/zvanja'])
      });
      return;
    }
    this.zvanjeService.add(this.zvanje).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/zvanja'])
    });
  }

  get pageTitle(): string {
    return this.id ? 'Izmena zvanja' : 'Dodavanje zvanja';
  }

}
