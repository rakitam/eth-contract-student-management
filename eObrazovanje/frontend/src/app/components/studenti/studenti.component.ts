import { Component } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {Student} from "./student.model";
import {StudentService} from "./student.service";
import {UtilsService} from "../../utils/utils.service";

@Component({
  selector: 'app-studenti',
  templateUrl: './studenti.component.html',
})
export class StudentiComponent {

  studenti: Student[] = [];

  params = {
    page: 0,
    size: 5,
    sortColumn: 'id',
    sortDirection: 'asc',
    search: ''
  }
  totalCount = 0;
  searchTerm = '';

  constructor(private studentService: StudentService, private modalService: NgbModal, public utils: UtilsService) {}

  ngOnInit(): void {
    this.ucitavanjeStudenata();
  }

  ucitavanjeStudenata(page = 0) {
    this.params.page = page;
    this.studentService.getAll(this.params).subscribe(response => {
      this.studenti = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjeStudenta(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.studentService.delete(id).subscribe(() => {
          this.ucitavanjeStudenata();
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
    this.ucitavanjeStudenata();
  }

}
