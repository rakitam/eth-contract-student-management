import { Component } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {Rok} from "./rok.model";
import {RokService} from "./rok.service";
import {UtilsService} from "../../utils/utils.service";

@Component({
  selector: 'app-rokovi',
  templateUrl: './rokovi.component.html',
})
export class RokoviComponent {

  rokovi: Rok[] = [];

  params = {
    page: 0,
    size: 5,
    sortColumn: 'id',
    sortDirection: 'asc',
    search: ''
  }
  totalCount = 0;
  searchTerm = '';

  constructor(private rokService: RokService, private modalService: NgbModal, public utils: UtilsService) {}

  ngOnInit(): void {
    this.ucitavanjeRoka();
  }

  ucitavanjeRoka(page = 0) {
    this.params.page = page;
    this.rokService.getAll(this.params).subscribe(response => {
      this.rokovi = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjeRoka(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.rokService.delete(id).subscribe(() => {
          this.ucitavanjeRoka();
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
    this.ucitavanjeRoka();
  }
}
