import {Component, Input} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {UplataService} from "./uplata.service";
import {Uplata} from "./uplata.model";
import {UtilsService} from "../../utils/utils.service";
import {ActivatedRoute} from "@angular/router";
import {Student} from "../studenti/student.model";

@Component({
  selector: 'app-uplate',
  templateUrl: './uplate.component.html',
})
export class UplateComponent {

  @Input() student: Student | null = null;

  uplate: Uplata[] = [];

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

  constructor(private uplataService: UplataService, private modalService: NgbModal, public utils: UtilsService,
              private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.ucitavanjeUplata();
  }

  ucitavanjeUplata(page = 0) {
    this.params.page = page;
    const username = this.route.snapshot.paramMap.get('username')!;
    if (username) {
      this.params.studentUsername = username;
    }
    this.uplataService.getAll(this.params).subscribe(response => {
      this.uplate = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  storniranjeUplate(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.uplataService.storniraj(id).subscribe(() => {
          this.ucitavanjeUplata();
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
    this.ucitavanjeUplata();
  }

}
