import { Component } from '@angular/core';
import {Student} from "../../studenti/student.model";
import {ActivatedRoute, Router} from "@angular/router";
import {PredmetService} from "../../predmet/predmet.service";
import {StudentService} from "../../studenti/student.service";
import {catchError} from "rxjs";
import {DokumentService} from "../dokument.service";
import {Dokument} from "../dokument.model";
import {UtilsService} from "../../../utils/utils.service";

@Component({
  selector: 'app-dodavanje-izmena-dokumenta',
  templateUrl: './dodavanje-izmena-dokumenta.component.html',
})
export class DodavanjeIzmenaDokumentaComponent {

  dokument: Dokument = {
    student: null,
    naziv: ''
  }
  id: number = 0;
  isDisabled = false;

  studenti: Student[] = [];
  file: any;

  constructor(private dokumentService: DokumentService, private router: Router, private route: ActivatedRoute,
              private studentService: StudentService, public utils: UtilsService) {}


  private ucitvanjeStudenata() {
    this.studentService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.studenti = response.body!;
    });
  }

  onFileChange(event: any){
    if (event.target.files.length > 0) {
      this.file = event.target.files[0];
    }
  }

  ngOnInit(): void {
    this.ucitvanjeStudenata();
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.isDisabled = true;
      this.dokumentService.get(this.id).subscribe(data => {
        this.dokument = data;
      });
    }
  }

  save() {
    if (this.id) {
      this.dokumentService.edit(this.dokument, this.file).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/dokumenti'])
      });
      return;
    }
    this.dokumentService.add(this.dokument, this.file).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/dokumenti'])
    });
  }

  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }

  get pageTitle(): string {
    return this.id ? 'Izmena dokumenta' : 'Dodavanje dokumenta';
  }
}
