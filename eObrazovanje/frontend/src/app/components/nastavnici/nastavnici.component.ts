import { Component } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {Nastavnik} from "./nastavnik.model";
import {NastavnikService} from "./nastavnik.service";
import {UtilsService} from "../../utils/utils.service";

@Component({
  selector: 'app-nastavnici',
  templateUrl: './nastavnici.component.html',
})
export class NastavniciComponent {

  nastavnici: Nastavnik[] = [];

  params = {
    page: 0,
    size: 5,
    sortColumn: 'id',
    sortDirection: 'asc',
    search: ''
  }
  totalCount = 0;
  searchTerm = '';

  constructor(private nastavnikService: NastavnikService, private modalService: NgbModal, public utils: UtilsService) {}

  ngOnInit(): void {
    this.ucitavanjeNastavnika();
  }

  ucitavanjeNastavnika(page = 0) {
    this.params.page = page;
    this.nastavnikService.getAll(this.params).subscribe(response => {
      this.nastavnici = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjeNastavnika(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.nastavnikService.delete(id).subscribe(() => {
          this.ucitavanjeNastavnika();
        });
      }
    })
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
    this.ucitavanjeNastavnika();
  }
}
