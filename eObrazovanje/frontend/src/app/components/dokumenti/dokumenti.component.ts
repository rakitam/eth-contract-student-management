import {Component, Input} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {Dokument} from "./dokument.model";
import {DokumentService} from "./dokument.service";
import {UtilsService} from "../../utils/utils.service";
import {AuthService} from "../../auth/auth.service";
import {Student} from "../studenti/student.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-dokumenti',
  templateUrl: './dokumenti.component.html',
})
export class DokumentiComponent {

  @Input() student: Student | null = null;

  dokumenti: Dokument[] = [];

  params = {
    page: 0,
    size: 5,
    sortColumn: 'id',
    sortDirection: 'asc',
    search: '',
    studentUsername: ''
  }
  totalCount = 0;
  searchTerm = '';

  constructor(private dokumentService: DokumentService, private modalService: NgbModal, public utils: UtilsService,
              private auth: AuthService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.ucitavanjeDokumenata();
  }

  ucitavanjeDokumenata(page = 0) {
    this.params.page = page;
    const username = this.route.snapshot.paramMap.get('username')!;
    if (username) {
      this.params.studentUsername = username;
    }
    this.dokumentService.getAll(this.params).subscribe(response => {
      this.dokumenti = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjeDokumenta(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.dokumentService.delete(id).subscribe(() => {
          this.ucitavanjeDokumenata();
        });
      }
    })
  }

  download(d: Dokument) {
    this.dokumentService.download(d.id!);
  }

  sort(column: string) {
    if (this.params.sortColumn === column) {
      if (this.params.sortDirection === 'desc') {
        this.params.sortColumn = 'id';
        this.params.sortDirection = 'asc';
      } else {
        this.params.sortDirection = 'desc';
      }
    } else {
      this.params.sortColumn = column;
      this.params.sortDirection = 'asc';
    }
    this.ucitavanjeDokumenata();
  }
}
