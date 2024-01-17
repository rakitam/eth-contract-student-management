import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";
import {PolaganjePredispitneObaveze} from "../polaganje-predispitne-obaveze.model";
import {PolaganjePredispitneObavezeService} from "../polaganje-predispitne-obaveze.service";
import {Student} from "../../studenti/student.model";
import {StudentService} from "../../studenti/student.service";
import {Predmet} from "../../predmet/predmet.model";
import {PredmetService} from "../../predmet/predmet.service";
import {PredispitnaObavezaService} from "../../predispitne-obaveze/predispitna-obaveza.service";
import {PredispitnaObaveza} from "../../predispitne-obaveze/predispitna-obaveza.model";

@Component({
  selector: 'app-dodavanje-izmena-polaganje',
  templateUrl: './dodavanje-izmena-polaganje.component.html',
})
export class DodavanjeIzmenaPolaganjeComponent {

  polaganjePredispitneObaveze: PolaganjePredispitneObaveze = {
    datum: '',
    polozio: false,
    bodovi: 0,
    predispitnaObaveza: null,
    student: null
  }
  id: number = 0;
  studenti: Student[] = [];
  predmeti: Predmet[] = [];
  predispitneObaveze: PredispitnaObaveza[] = [];

  oznaceniPredmet: Predmet | null = null;

  constructor(private polaganjePredispitneObavezeService: PolaganjePredispitneObavezeService, private router: Router, private route: ActivatedRoute,
              private predmetService: PredmetService, private studentService: StudentService, private predispitneObavezeService: PredispitnaObavezaService) {}

  //TODO: Da dovlaci predmete samo za odabranog studenta
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

  ucitavanjePredispitnihObaveza() {
    this.polaganjePredispitneObaveze.predispitnaObaveza = null;
    this.predispitneObavezeService.getAll(this.oznaceniPredmet?.id).subscribe(response => {
      this.predispitneObaveze = response.body!;
    });
  }

  ngOnInit(): void {
    this.ucitavanjePredmeta();
    this.ucitvanjeStudenata();
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.ucitavanjePredispitnihObaveza();
      this.polaganjePredispitneObavezeService.get(this.id).subscribe(data => {
        this.polaganjePredispitneObaveze = data;
        this.oznaceniPredmet = this.polaganjePredispitneObaveze.predispitnaObaveza?.predmet!;
      });
    }
  }

  save() {
    if (this.id) {
      this.polaganjePredispitneObavezeService.edit(this.polaganjePredispitneObaveze).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/polaganje-predispitne-obaveze'])
      });
      return;
    }
    this.polaganjePredispitneObavezeService.add(this.polaganjePredispitneObaveze).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/polaganje-predispitne-obaveze'])
    });
  }


  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }
}
