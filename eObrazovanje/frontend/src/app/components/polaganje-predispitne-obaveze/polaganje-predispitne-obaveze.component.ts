import {Component, Input} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {PolaganjePredispitneObaveze} from "./polaganje-predispitne-obaveze.model";
import {PolaganjePredispitneObavezeService} from "./polaganje-predispitne-obaveze.service";
import {UtilsService} from "../../utils/utils.service";
import {Student} from "../studenti/student.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-polaganje-predispitne-obaveze',
  templateUrl: './polaganje-predispitne-obaveze.component.html'
})
export class PolaganjePredispitneObavezeComponent {

  @Input() student: Student | null = null;

  polaganjePredispitneObavezeList: PolaganjePredispitneObaveze[] = [];

  queryParams = {
    page: 0,
    size: 5,
    studentUsername: ''
  }
  totalCount = 0;

  constructor(private polaganjePredispitneObavezeService: PolaganjePredispitneObavezeService, private modalService: NgbModal,
              public utils: UtilsService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.ucitavanjePredispitnihObaveze();
  }

  ucitavanjePredispitnihObaveze(page = 0) {
    this.queryParams.page = page;
    const username = this.route.snapshot.paramMap.get('username')!;
    if (username) {
      this.queryParams.studentUsername = username;
    }
    this.polaganjePredispitneObavezeService.getAll(this.queryParams).subscribe(response => {
      this.polaganjePredispitneObavezeList = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjePredispitneObaveze(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.polaganjePredispitneObavezeService.delete(id).subscribe(() => {
          this.ucitavanjePredispitnihObaveze();
        });
      }
    })
  }
}
