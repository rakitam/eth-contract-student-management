import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";
import {StudentPredmet} from "../student-predmet.model";
import {StudentPredmetService} from "../student-predmet.service";
import {PredmetService} from "../../predmet/predmet.service";
import {StudentService} from "../../studenti/student.service";
import {Predmet} from "../../predmet/predmet.model";
import {Student} from "../../studenti/student.model";
import {UtilsService} from "../../../utils/utils.service";

@Component({
  selector: 'app-dodavanje-izmena-student-predmet',
  templateUrl: './dodavanje-izmena-student-predmet.component.html',
})
export class DodavanjeIzmenaStudentPredmetComponent {

  studentPredmet: StudentPredmet = {
    polozio: false,
    osvojeniBodovi: 0,
    predmet: null,
    student: null
  }
  id: number = 0;
  isDisabled = false;

  studenti: Student[] = [];
  predmeti: Predmet[] = [];
  polaznici: StudentPredmet[] = [];

  constructor(private studentPredmetService: StudentPredmetService, private router: Router, private route: ActivatedRoute,
              private predmetService: PredmetService, private studentService: StudentService) {}

  private ucitavanjePredmeta() {
    this.predmetService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.predmeti = response.body!;
    });
  }

  private ucitvanjeStudenata() {
    this.studentService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.studenti = response.body!;
    });
  }

  private ucitavanjePolaznika() {
    this.studentPredmetService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.polaznici = response.body!;
    });
  }

  ngOnInit(): void {
    this.ucitavanjePredmeta();
    this.ucitvanjeStudenata();
    this.ucitavanjePolaznika();
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.isDisabled = true;
      this.studentPredmetService.get(this.id).subscribe(data => {
        this.studentPredmet = data;
      });
    }
  }

  save() {
    // Check if a student is already enlisted in the selected subject
    const existingStudent = this.polaznici.find(polaznik =>
      polaznik.predmet?.id === this.studentPredmet.predmet?.id &&
      polaznik.student?.id === this.studentPredmet.student?.id &&
      polaznik.id !== this.studentPredmet.id
    );

    if (existingStudent) {
      alert('Student je već upisan na ovaj predmet.');
      return;
    }

    if (this.id) {
      this.studentPredmetService.edit(this.studentPredmet).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/student-predmet'])
      });
      return;
    }
    this.studentPredmetService.add(this.studentPredmet).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/student-predmet'])
    });
  }

  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }

  get pageTitle(): string {
    return this.id ? 'Izmena polaznika' : 'Dodavanje polaznika';
  }
}
