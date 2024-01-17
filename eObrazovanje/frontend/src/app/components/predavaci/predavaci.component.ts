import { Component } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {Predaje} from "./predaje.model";
import {PredavaciService} from "./predavaci.service";
import {UtilsService} from "../../utils/utils.service";

@Component({
  selector: 'app-predavaci',
  templateUrl: './predavaci.component.html',
})
export class PredavaciComponent {

  predavaci: Predaje[] = [];

  params = {
    page: 0,
    size: 5,
    sortColumn: 'id',
    sortDirection: 'asc',
    search: ''
  }
  totalCount = 0;
  searchTerm = '';

  constructor(private predavaciService: PredavaciService, private modalService: NgbModal, public utils: UtilsService) {}

  ngOnInit(): void {
    this.ucitavanjePredaje();
  }

  ucitavanjePredaje(page = 0) {
    this.params.page = page;
    this.predavaciService.getAll(this.params).subscribe(response => {
      this.predavaci = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjePredaje(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.predavaciService.delete(id).subscribe(() => {
          this.ucitavanjePredaje();
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
    this.ucitavanjePredaje();
  }

}
