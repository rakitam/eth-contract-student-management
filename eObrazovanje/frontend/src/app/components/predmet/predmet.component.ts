import {Component, OnInit} from '@angular/core';
import {PredmetService} from "./predmet.service";
import {Predmet} from "./predmet.model";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {UtilsService} from "../../utils/utils.service";

@Component({
  selector: 'app-predmet',
  templateUrl: './predmet.component.html',
})
export class PredmetComponent implements OnInit {

  predmeti: Predmet[] = [];

  params = {
    page: 0,
    size: 5,
    sortColumn: 'id',
    sortDirection: 'asc',
    search: '',
  }
  totalCount = 0;
  searchTerm = '';

  constructor(private predmetService: PredmetService, private modalService: NgbModal, public utils: UtilsService) {}

  ngOnInit(): void {
    this.ucitavanjePredmeta();
  }

  ucitavanjePredmeta(page = 0) {
    this.params.page = page;
    this.predmetService.getAll(this.params).subscribe(response => {
      this.predmeti = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjePredmeta(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.predmetService.delete(id).subscribe(() => {
          this.ucitavanjePredmeta();
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
    this.ucitavanjePredmeta();
  }

}
