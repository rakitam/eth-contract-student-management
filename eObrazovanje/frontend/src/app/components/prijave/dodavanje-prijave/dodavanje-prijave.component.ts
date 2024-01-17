import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";
import {Prijava} from "../prijava.model";
import {PrijavaService} from "../prijava.service";
import {Student} from "../../studenti/student.model";
import {StudentService} from "../../studenti/student.service";
import {IspitniRokService} from "../../ispitni-rokovi/ispitni-rok.service";
import {IspitniRok} from "../../ispitni-rokovi/ispitni-rok.model";
import {UtilsService} from "../../../utils/utils.service";
import {AuthService} from "../../../auth/auth.service";

@Component({
  selector: 'app-dodavanje-prijave',
  templateUrl: './dodavanje-prijave.component.html',
})
export class DodavanjePrijaveComponent {

  prijava: Prijava = {
    student: null,
    datumPrijave: new Date(),
    ispitniRok: null
  }

  id: number = 0;
  isDisabled = false;

  studenti: Student[] = [];
  ispitniRokovi: IspitniRok[] = [];
  studentDisabled = false;

  constructor(private prijavaService: PrijavaService, private router: Router, private route: ActivatedRoute,
              public utils: UtilsService, public auth: AuthService, private studentService: StudentService,
              private ispitniRokService: IspitniRokService) {
  }

  private ucitvanjeStudenata(username = '') {
    this.studentService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.studenti = response.body!;
      this.prijava.student = this.studenti.find(s => s.username === username) || null;
    });
  }

  ucitavanjeIspitnihRokova(username = '') {
    this.ispitniRokService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER, student: username}).subscribe(response => {
      this.ispitniRokovi = response.body!.filter((t) => new Date(t!.rok!.pocetak)< new Date && new Date(t!.rok!.kraj)>new Date());
    });
  }

  ngOnInit(): void {
    const username = this.route.snapshot.queryParams['username'];
    if(username) {
      this.studentDisabled = true;
    }
    this.ucitvanjeStudenata(username);
    this.ucitavanjeIspitnihRokova(username);
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.isDisabled = true;
      this.prijavaService.get(this.id).subscribe(data => {
        this.prijava = data;
        this.prijava.student = data.student;
      });
    }
  }


  get stanjeNaRacunu() {
    if(!this.id) {
      return this.prijava?.student ? this.prijava.student.stanje - 200 : "";
    } else {
      return this.prijava?.student?.stanje;
    }
  }

  save() {
    if(this.id) {
      this.prijavaService.edit(this.prijava).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/prijave'])
      });
    }
    this.prijavaService.add(this.prijava).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/prijave'])
    });
  }

  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }

  get pageTitle(): string {
    return this.id ? 'Izmena prijave' : 'Prijava ispita';
  }

}
