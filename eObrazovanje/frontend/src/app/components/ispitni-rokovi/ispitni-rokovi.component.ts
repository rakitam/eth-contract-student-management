import { Component } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {IspitniRok} from "./ispitni-rok.model";
import {IspitniRokService} from "./ispitni-rok.service";
import {UtilsService} from "../../utils/utils.service";

@Component({
  selector: 'app-ispitni-rokovi',
  templateUrl: './ispitni-rokovi.component.html',
})
export class IspitniRokoviComponent {

  ispitniRokovi: IspitniRok[] = [];

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

  constructor(private ispitniRokService: IspitniRokService, private modalService: NgbModal, public utils: UtilsService) {}

  ngOnInit(): void {
    this.ucitavanjeIspitnihRokova();
  }

  ucitavanjeIspitnihRokova(page = 0) {
    this.params.page = page;
    this.ispitniRokService.getAll(this.params).subscribe(response => {
      this.ispitniRokovi = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjeIspitnogRoka(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.ispitniRokService.delete(id).subscribe(() => {
          this.ucitavanjeIspitnihRokova();
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
    this.ucitavanjeIspitnihRokova();
  }
}
