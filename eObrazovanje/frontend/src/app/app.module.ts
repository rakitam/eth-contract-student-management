import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {NgbDateAdapter, NgbDateParserFormatter, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './header/header.component';
import {RouterModule, Routes} from "@angular/router";
import { HomeComponent } from './home/home.component';
import { PredmetComponent } from './components/predmet/predmet.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { DodavanjeIzmenaPredmetComponent } from './components/predmet/dodavanje-izmena-predmet/dodavanje-izmena-predmet.component';
import {FormsModule} from "@angular/forms";
import { DeleteModalComponent } from './delete-modal/delete-modal.component';
import { ZvanjaComponent } from './components/zvanja/zvanja.component';
import { DodavanjeIzmenaZvanjeComponent } from './components/zvanja/dodavanje-izmena-zvanje/dodavanje-izmena-zvanje.component';
import { RokoviComponent } from './components/rokovi/rokovi.component';
import { DodavanjeIzmenaRokaComponent } from './components/rokovi/dodavanje-izmena-roka/dodavanje-izmena-roka.component';
import {CustomAdapter} from "./utils/custom-adapter.service";
import {CustomDateParserFormatter} from "./utils/custom-date-parser-formatter.service";
import { NastavniciComponent } from './components/nastavnici/nastavnici.component';
import { DodavanjeIzmenaNastavnikComponent } from './components/nastavnici/dodavanje-izmena-nastavnik/dodavanje-izmena-nastavnik.component';
import { StudentiComponent } from './components/studenti/studenti.component';
import { DodavanjeIzmenaStudentComponent } from './components/studenti/dodavanje-izmena-student/dodavanje-izmena-student.component';
import { PredavaciComponent } from './components/predavaci/predavaci.component';
import { DodavanjeIzmenaPredavacaComponent } from './components/predavaci/dodavanje-izmena-predavaca/dodavanje-izmena-predavaca.component';
import { StudentPredmetComponent } from './components/student-predmet/student-predmet.component';
import { DodavanjeIzmenaStudentPredmetComponent } from './components/student-predmet/dodavanje-izmena-student-predmet/dodavanje-izmena-student-predmet.component';
import { PredispitneObavezeComponent } from './components/predispitne-obaveze/predispitne-obaveze.component';
import { DodavanjeIzmenaPredispitneObavezeComponent } from './components/predispitne-obaveze/dodavanje-izmena-predispitne-obaveze/dodavanje-izmena-predispitne-obaveze.component';
import { PolaganjePredispitneObavezeComponent } from './components/polaganje-predispitne-obaveze/polaganje-predispitne-obaveze.component';
import { DodavanjeIzmenaPolaganjeComponent } from './components/polaganje-predispitne-obaveze/dodavanje-izmena-polaganje/dodavanje-izmena-polaganje.component';
import { UplateComponent } from './components/uplate/uplate.component';
import { DodavanjeUplataComponent } from './components/uplate/dodavanje-uplata/dodavanje-uplata.component';
import { IspitiComponent } from './components/ispiti/ispiti.component';
import { IzmenaIspitaComponent } from './components/ispiti/izmena-ispita/izmena-ispita.component';
import { IspitniRokoviComponent } from './components/ispitni-rokovi/ispitni-rokovi.component';
import { DodavanjeIzmenaIspitnogRokaComponent } from './components/ispitni-rokovi/dodavanje-izmena-ispitnog-roka/dodavanje-izmena-ispitnog-roka.component';
import { PrijaveComponent } from './components/prijave/prijave.component';
import { DodavanjePrijaveComponent } from './components/prijave/dodavanje-prijave/dodavanje-prijave.component';
import { DokumentiComponent } from './components/dokumenti/dokumenti.component';
import { DodavanjeIzmenaDokumentaComponent } from './components/dokumenti/dodavanje-izmena-dokumenta/dodavanje-izmena-dokumenta.component';
import {JwtInterceptor} from "./auth/jwt.interceptor";
import { LoginComponent } from './login/login.component';
import { PromenaLozinkeComponent } from './auth/promena-lozinke/promena-lozinke.component';
import {AuthGuard} from "./auth/auth.guard";
import { StudentProfileComponent } from './auth/student-profile/student-profile.component';
import { SortComponent } from './sort/sort.component';
import {BrowserAnimationsModule, NoopAnimationsModule} from '@angular/platform-browser/animations';
import {IzmenaIspitaStudentPageComponent} from "./components/ispiti/izmena-ispita/izmena-ispita-student-page.component";
import { EthereumModalComponent } from './ethereum-modal/ethereum-modal.component';
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'predmeti', component: PredmetComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK', 'STUDENT']}},
  { path: 'predmeti/:id', component: DodavanjeIzmenaPredmetComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'predmeti-dodavanje', component: DodavanjeIzmenaPredmetComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},

  { path: 'zvanja', component: ZvanjaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},
  { path: 'zvanja/:id', component: DodavanjeIzmenaZvanjeComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},
  { path: 'zvanje-dodavanje', component: DodavanjeIzmenaZvanjeComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},

  { path: 'rokovi', component: RokoviComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK', 'STUDENT']}},
  { path: 'rokovi/:id', component: DodavanjeIzmenaRokaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},
  { path: 'rok-dodavanje', component: DodavanjeIzmenaRokaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},

  { path: 'nastavnici', component: NastavniciComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'nastavnici/:id', component: DodavanjeIzmenaNastavnikComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},
  { path: 'nastavnik-dodavanje', component: DodavanjeIzmenaNastavnikComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},

  { path: 'studenti', component: StudentiComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'studenti/:id', component: DodavanjeIzmenaStudentComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'STUDENT']}},
  { path: 'student-dodavanje', component: DodavanjeIzmenaStudentComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},

  { path: 'predavaci', component: PredavaciComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK', 'STUDENT']}},
  { path: 'predavaci/:id', component: DodavanjeIzmenaPredavacaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},
  { path: 'predavac-dodavanje', component: DodavanjeIzmenaPredavacaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},

  { path: 'student-predmet', component: StudentPredmetComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'student-predmet/:id', component: DodavanjeIzmenaStudentPredmetComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'student-predmet-dodavanje', component: DodavanjeIzmenaStudentPredmetComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},

  { path: 'predispitne-obaveze', component: PredispitneObavezeComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'predispitne-obaveze/:id', component: DodavanjeIzmenaPredispitneObavezeComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'predispitne-obaveze-dodavanje', component: DodavanjeIzmenaPredispitneObavezeComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},

  { path: 'polaganje-predispitne-obaveze', component: PolaganjePredispitneObavezeComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'polaganje-predispitne-obaveze/:id', component: DodavanjeIzmenaPolaganjeComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'polaganje-predispitne-obaveze-dodavanje', component: DodavanjeIzmenaPolaganjeComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},

  { path: 'uplate', component: UplateComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'STUDENT']}},
  { path: 'uplata-dodavanje', component: DodavanjeUplataComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},

  { path: 'ispiti', component: IspitiComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'ispiti/:id', component: IzmenaIspitaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},
  { path: 'profile/:username/ispiti/:id', component: IzmenaIspitaStudentPageComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},
  { path: 'ispit-dodavanje', component: IzmenaIspitaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'profile/:username/ispit-dodavanje', component: IzmenaIspitaStudentPageComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN']}},

  { path: 'ispitni-rokovi', component: IspitniRokoviComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'ispitni-rokovi/:id', component: DodavanjeIzmenaIspitnogRokaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'ispitni-rok-dodavanje', component: DodavanjeIzmenaIspitnogRokaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},

  { path: 'prijave', component: PrijaveComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'NASTAVNIK']}},
  { path: 'prijava-dodavanje', component: DodavanjePrijaveComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'STUDENT']}},
  { path: 'prijave/:id' , component: DodavanjePrijaveComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'STUDENT']}},

  { path: 'dokumenti', component: DokumentiComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'STUDENT']}},
  { path: 'dokumenti/:id', component: DodavanjeIzmenaDokumentaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'STUDENT']}},
  { path: 'dokument-dodavanje', component: DodavanjeIzmenaDokumentaComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'STUDENT']}},

  { path: 'login', component: LoginComponent},
  { path: 'promena-lozinke', component: PromenaLozinkeComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'STUDENT', 'NASTAVNIK']}},
  { path: 'profile/:username', component: StudentProfileComponent, canActivate: [AuthGuard], data: {roles: ['ADMIN', 'STUDENT', 'NASTAVNIK']}},
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    PredmetComponent,
    DodavanjeIzmenaPredmetComponent,
    DeleteModalComponent,
    ZvanjaComponent,
    DodavanjeIzmenaZvanjeComponent,
    RokoviComponent,
    DodavanjeIzmenaRokaComponent,
    NastavniciComponent,
    DodavanjeIzmenaNastavnikComponent,
    StudentiComponent,
    DodavanjeIzmenaStudentComponent,
    PredavaciComponent,
    DodavanjeIzmenaPredavacaComponent,
    StudentPredmetComponent,
    DodavanjeIzmenaStudentPredmetComponent,
    PredispitneObavezeComponent,
    DodavanjeIzmenaPredispitneObavezeComponent,
    PolaganjePredispitneObavezeComponent,
    DodavanjeIzmenaPolaganjeComponent,
    UplateComponent,
    DodavanjeUplataComponent,
    IspitiComponent,
    IzmenaIspitaComponent,
    IzmenaIspitaStudentPageComponent,
    IspitniRokoviComponent,
    DodavanjeIzmenaIspitnogRokaComponent,
    PrijaveComponent,
    DodavanjePrijaveComponent,
    DokumentiComponent,
    DodavanjeIzmenaDokumentaComponent,
    LoginComponent,
    PromenaLozinkeComponent,
    StudentProfileComponent,
    SortComponent,
    EthereumModalComponent
  ],
  imports: [
    BrowserModule, RouterModule.forRoot(routes), HttpClientModule, FormsModule,
    NgbModule,
    NoopAnimationsModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    BrowserAnimationsModule
  ],
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    MatDialog
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
