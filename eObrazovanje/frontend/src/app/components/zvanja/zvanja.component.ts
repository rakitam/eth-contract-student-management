import { Component } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteModalComponent} from "../../delete-modal/delete-modal.component";
import {ZvanjeService} from "./zvanje.service";
import {Zvanje} from "./zvanje.model";

@Component({
  selector: 'app-zvanja',
  templateUrl: './zvanja.component.html',
})
export class ZvanjaComponent {

  zvanja: Zvanje[] = [];

  constructor(private zvanjeService: ZvanjeService, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.ucitavanjeZvanja();
  }

  private ucitavanjeZvanja() {
    this.zvanjeService.getAll().subscribe(response => {
      this.zvanja = response.body!;
    });
  }

  brisanjeZvanja(id: number | any) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.result.then(res => {
      if(res) {
        this.zvanjeService.delete(id).subscribe(() => {
          this.ucitavanjeZvanja();
        });
      }
    })
  }
}
