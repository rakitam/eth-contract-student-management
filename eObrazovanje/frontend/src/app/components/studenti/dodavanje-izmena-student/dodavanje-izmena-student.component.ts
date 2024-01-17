import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Student} from "../student.model";
import {StudentService} from "../student.service";
import {UtilsService} from "../../../utils/utils.service";

@Component({
  selector: 'app-dodavanje-izmena-student',
  templateUrl: './dodavanje-izmena-student.component.html',
})
export class DodavanjeIzmenaStudentComponent {

  student: Student = {
    ime: '',
    prezime: '',
    jmbg: '',
    adresa: '',
    brojTelefona: '',
    brojIndeksa: '',
    username: '',
    tekuciRacun: '',
    stanje: 0,
  }
  id: string = '';
  originalUsername: string = '';
  originalJmbg: string = '';
  isDisabled = false;

  constructor(private studentService: StudentService, private utilsService: UtilsService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.isDisabled = true;
      this.studentService.get(this.id).subscribe(data => {
        this.student = data;
        this.originalUsername = data.username;
        this.originalJmbg = data.jmbg;
      });
    }
  }

  save() {
    // Check if a username is entered
    if (!this.student?.username) {
      alert('Morate uneti korisničko ime.');
      return;
    }

    // Add or edit the student as necessary
    if (this.id) {
      this.studentService.edit(this.student).subscribe(() => {
        this.router.navigate(['/studenti']);
      }, error => {
        alert(error.error);
      });
    } else {
      this.studentService.add(this.student).subscribe(() => {
        this.router.navigate(['/studenti']);
      }, error => {
        alert(error.error);
      });
    }
  }

  get pageTitle(): string {
    return this.id ? 'Izmena studenta' : 'Dodavanje studenta';
  }

}
