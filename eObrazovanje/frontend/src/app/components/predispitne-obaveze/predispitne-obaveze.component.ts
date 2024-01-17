import { Component } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {PredispitnaObaveza} from "./predispitna-obaveza.model";
import {PredispitnaObavezaService} from "./predispitna-obaveza.service";

@Component({
  selector: 'app-predispitne-obaveze',
  templateUrl: './predispitne-obaveze.component.html',
})
export class PredispitneObavezeComponent {

  predispitneObaveze: PredispitnaObaveza[] = [];

  pageable = {
    page: 0,
    size: 5,
  }
  totalCount = 0;

  constructor(private predispitnaObavezaService: PredispitnaObavezaService, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.ucitavanjePredispitnaObaveza();
  }

  ucitavanjePredispitnaObaveza(page = 0) {
    this.pageable.page = page;
    this.predispitnaObavezaService.getAll(this.pageable).subscribe(response => {
      this.predispitneObaveze = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  brisanjePredispitneObaveze(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.predispitnaObavezaService.delete(id).subscribe(() => {
          this.ucitavanjePredispitnaObaveza();
        });
      }
    })
  }
}
