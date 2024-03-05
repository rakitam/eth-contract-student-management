import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-ethereum-modal',
  templateUrl: './ethereum-modal.component.html',
  styleUrls: ['./ethereum-modal.component.css']
})
export class EthereumModalComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    console.log('Received data:', data);
  }
}
