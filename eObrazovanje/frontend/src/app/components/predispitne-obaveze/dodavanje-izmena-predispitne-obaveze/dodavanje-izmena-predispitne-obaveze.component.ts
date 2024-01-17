import {Component} from '@angular/core';
import {Predmet} from "../../predmet/predmet.model";
import {ActivatedRoute, Router} from "@angular/router";
import {PredmetService} from "../../predmet/predmet.service";
import {catchError} from "rxjs";
import {PredispitnaObaveza} from "../predispitna-obaveza.model";
import {PredispitnaObavezaService} from "../predispitna-obaveza.service";
import {TipPredispitneObaveze} from "../tip-predispitne-obaveze.enum";

@Component({
  selector: 'app-dodavanje-izmena-predispitne-obaveze',
  templateUrl: './dodavanje-izmena-predispitne-obaveze.component.html',
})
export class DodavanjeIzmenaPredispitneObavezeComponent {

  predispitnaObaveza: PredispitnaObaveza = {
    naziv: '',
    minBodova: 0,
    predmet: null,
    tip: TipPredispitneObaveze.TEST,
  }
  id: number = 0;

  tipoviPredispitnihObaveza: any[] = Object.keys(TipPredispitneObaveze);
  predmeti: Predmet[] = [];

  constructor(private predispitnaObavezaService: PredispitnaObavezaService, private router: Router, private route: ActivatedRoute,
              private predmetService: PredmetService) {}

  private ucitavanjePredmeta() {
    this.predmetService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER}).subscribe(response => {
      this.predmeti = response.body!;
    });
  }

  ngOnInit(): void {
    this.ucitavanjePredmeta();
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.predispitnaObavezaService.get(this.id).subscribe(data => {
        this.predispitnaObaveza = data;
      });
    }
  }

  save() {
    if (this.id) {
      this.predispitnaObavezaService.edit(this.predispitnaObaveza).pipe(catchError(err => {
        // TODO handle error
        return err;
      })).subscribe(data => {
        this.router.navigate(['/predispitne-obaveze'])
      });
      return;
    }
    this.predispitnaObavezaService.add(this.predispitnaObaveza).pipe(catchError(err => {
      // TODO handle error
      return err;
    })).subscribe(data => {
      this.router.navigate(['/predispitne-obaveze'])
    });
  }

  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }

  get pageTitle(): string {
    return this.id ? 'Izmena predispitne obaveze' : 'Dodavanje predispitne obaveze';
  }

}
