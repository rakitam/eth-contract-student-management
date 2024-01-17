import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";
import {Uplata} from "../uplata.model";
import {StudentService} from "../../studenti/student.service";
import {Student} from "../../studenti/student.model";
import {UplataService} from "../uplata.service";

@Component({
  selector: 'app-dodavanje-izmena-uplata',
  templateUrl: './dodavanje-uplata.component.html',
})
export class DodavanjeUplataComponent {

  uplata: Uplata = {
    student: null,
    datum: '',
    iznos: 0
  }

  studenti: Student[] = [];

  constructor(private uplataService: UplataService, private router: Router, private route: ActivatedRoute,
              private studentService: StudentService) {}

  private ucitvanjeStudenata() {
    this.studentService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.studenti = response.body!;
    });
  }

  ngOnInit(): void {
    this.ucitvanjeStudenata();
  }

  save() {
    this.uplataService.add(this.uplata).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/uplate'])
    });
  }

  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }
}
