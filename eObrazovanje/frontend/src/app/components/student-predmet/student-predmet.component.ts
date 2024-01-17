import { Component } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {StudentPredmet} from "./student-predmet.model";
import {StudentPredmetService} from "./student-predmet.service";
import {UtilsService} from "../../utils/utils.service";

@Component({
  selector: 'app-student-predmet',
  templateUrl: './student-predmet.component.html',
})
export class StudentPredmetComponent {

  studentPredmetList: StudentPredmet[] = [];

  params = {
    page: 0,
    size: 5,
    sortColumn: 'id',
    sortDirection: 'asc',
    search: '',
    minBodova: 0,
    maxBodova: 100,
  }
  totalCount = 0;
  searchTerm = '';

  constructor(private studentPredmetService: StudentPredmetService, private modalService: NgbModal, public utils: UtilsService) {}

  ngOnInit(): void {
    this.ucitavanjeStudentPredmet();
  }

  ucitavanjeStudentPredmet(page = 0) {
    this.params.page = page;
    this.studentPredmetService.getAll(this.params).subscribe(response => {
      this.studentPredmetList = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjeStudentPredmet(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.studentPredmetService.delete(id).subscribe(() => {
          this.ucitavanjeStudentPredmet();
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
    this.ucitavanjeStudentPredmet();
  }
}
