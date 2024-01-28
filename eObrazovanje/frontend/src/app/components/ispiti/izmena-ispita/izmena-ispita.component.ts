import { Component } from '@angular/core';
import {PredavaciService} from "../../predavaci/predavaci.service";
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs";
import {Ispit} from "../ispit.model";
import {IspitService} from "../ispit.service";
import {Predaje} from "../../predavaci/predaje.model";
import {Student} from "../../studenti/student.model";
import {StudentPredmetService} from "../../student-predmet/student-predmet.service";
import {RokService} from "../../rokovi/rok.service";
import {CustomAdapter} from "../../../utils/custom-adapter.service";
import {EthereumService} from "../../ethereum.service";
import {EthereumModalComponent} from "../../../ethereum-modal/ethereum-modal.component";
import {MatDialog} from "@angular/material/dialog";
import {MatProgressSpinner} from "@angular/material/progress-spinner";

@Component({
  selector: 'app-izmena-ispita',
  templateUrl: './izmena-ispita.component.html',
})
export class IzmenaIspitaComponent {

  ispit: Ispit = {
    predaje: null,
    datum: '',
    student: null,
    bodovi: 0,
    ocena: null,
    konacno: false
  }
  id: number = 0;

  predavaci: Predaje[] = [];
  studenti: Student[] = [];
  aktivniRok: any | null = null;
  isAddingFromProfile = false;
  isSubmitting = false;


  constructor(private ispitService: IspitService, private router: Router, private route: ActivatedRoute,
              private predavaciService: PredavaciService, private studentPredmetService: StudentPredmetService,
              private ethereumService: EthereumService, private rokService: RokService, private customAdapter: CustomAdapter,
              private dialog: MatDialog) {}

  private ucivanjePredavaca() {
    this.predavaciService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER, aktivniZaRok: true}).subscribe(response => {
      this.predavaci = response.body!;
    });
  }

  ucitvanjeStudenata() {
    this.studentPredmetService.getAll({page: 0, size: Number.MAX_SAFE_INTEGER, predmetId: this.ispit.predaje?.predmet?.id}).subscribe(response => {
      this.studenti = response.body!.map(s => s.student!);
    });
  }

  private ucitavanjeAktivniRok() {
    this.rokService.getAktivniRok().subscribe(data => {
      this.aktivniRok = {
        ...data,
        pocetak: this.customAdapter.fromModel(`${data.pocetak}`),
        kraj: this.customAdapter.fromModel(`${data.kraj}`),
      };
    });
  }

  ngOnInit(): void {
    this.ucivanjePredavaca();
    this.ucitavanjeAktivniRok();
    this.ucitvanjeStudenata();
    this.id = +this.route.snapshot.paramMap.get('id')!;
    if (this.id) {
      this.ispitService.get(this.id).subscribe(data => {
        console.log(data);
        this.ispit = data;
        const ocena = this.izracunajOcenu();
        this.ispit.ocena = ocena;
      });
    }
  }

  izracunajOcenuOnChange(): void {
    const izracunataOcena = this.izracunajOcenu();
    this.ispit.ocena = izracunataOcena;
  }

  izracunajOcenu(): number | null {
    const bodovi = this.ispit.bodovi;

    if (bodovi === null || bodovi === undefined) {
      return null;
    }

    if (bodovi >= 0 && bodovi <= 50) {
      return 5;
    } else if (bodovi <= 60) {
      return 6;
    } else if (bodovi <= 70) {
      return 7;
    } else if (bodovi <= 80) {
      return 8;
    } else if (bodovi <= 90) {
      return 9;
    } else if (bodovi <= 100) {
      return 10;
    } else {
      return 0;
    }
  }

  save() {
    if (!this.ispit.datum || !this.ispit.student || !this.ispit.predaje) {
      alert('Niste popunili sva polja');
      return;
    }
    if (this.id) {
      this.ispitService.edit(this.ispit).pipe(catchError(err => {
        this.handleBackendError(err);
        throw err;
      })).subscribe(data => {
        if (this.ispit.konacno) {
          this.triggerEthereumTransaction();
        }
        this.router.navigate(['/ispiti'])
      });
      return;
    }
    this.ispitService.add(this.ispit).pipe(catchError(err => {
      this.handleBackendError(err);
      throw err;
    })).subscribe(data => {
      if (this.ispit.konacno) {
        this.triggerEthereumTransaction();
      }
      this.router.navigate(['/ispiti'])
    });
  }

  private triggerEthereumTransaction() {
    this.isSubmitting = true;
    const ethereumData = {
      studentId: this.ispit.student?.brojIndeksa,
      courseId: this.ispit.predaje?.predmet?.oznaka,
      grade: this.ispit.ocena,
      date: this.ispit.datum,
      professorId: this.ispit.predaje?.nastavnik?.id,
    };

    this.ethereumService.triggerTransaction(ethereumData).subscribe(
      (transactionHash) => {
        console.log('Transaction triggered successfully.');
        this.showAlert('Ethereum transakcija uspesno izvrsena.', transactionHash);
      },
      (error) => {
        console.error('Error triggering transaction:', error);
        this.showAlert('Doslo je do greske pri izvrsavanju transakcije.', '');
      }
    );
  }

  private handleBackendError(error: any): void {
    let errorMessage = 'Greška prilikom čuvanja ispita';
    if (error && error.error) {
      errorMessage = error.error;
    }
    alert(errorMessage);
  }

  private showAlert(message: string, transactionHash: string): void {
    const ethernalLink = `https://app.tryethernal.com/transaction/${transactionHash}`;
    const dialogRef = this.dialog.open(EthereumModalComponent, {
      data: {
        title: 'Detalji transakcije',
        message: message,
        link: ethernalLink
      }
    });
    dialogRef.afterClosed().subscribe(() => {
      this.isSubmitting = false;
      const username = this.route.snapshot.paramMap.get('username');
      this.router.navigate(['/profile', username]);
    });
  }

  compareWithId(o1: any, o2: any) {
    return o1?.id === o2?.id
  }

  get pageTitle(): string {
    return this.id ? 'Izmena ispita' : 'Dodavanje ispita';
  }
}
