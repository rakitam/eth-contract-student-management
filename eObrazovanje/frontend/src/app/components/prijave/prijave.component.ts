import {Component, Input} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {PrijavaService} from "./prijava.service";
import {Prijava} from "./prijava.model";
import {UtilsService} from "../../utils/utils.service";
import {Student} from "../studenti/student.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-prijave',
  templateUrl: './prijave.component.html',
})
export class PrijaveComponent {

  @Input() student: Student | null = null;

  prijave: Prijava[] = [];

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

  constructor(private prijavaService: PrijavaService, private modalService: NgbModal, public utils: UtilsService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.ucitavanjePrijava();
  }

  ucitavanjePrijava(page = 0) {
    this.params.page = page;
    const username = this.route.snapshot.paramMap.get('username')!;
    if (username) {
      this.params.studentUsername = username;
    }
    this.prijavaService.getAll(this.params).subscribe(response => {
      this.prijave = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
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
    this.ucitavanjePrijava();
  }

  // brisanjePrijave(id: number | any) {
  //   const modalRef = this.modalService.open(DeleteModalComponent);
  //   modalRef.result.then(res => {
  //     if(res) {
  //       this.prijavaService.delete(id).subscribe(() => {
  //         this.ucitavanjePrijava();
  //       });
  //     }
  //   })
  // }
}
