import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-sort',
  templateUrl: './sort.component.html',
})
export class SortComponent {

  @Input() column = '';
  @Input() params: {sortColumn?: string, sortDirection?: string} = {};
}
