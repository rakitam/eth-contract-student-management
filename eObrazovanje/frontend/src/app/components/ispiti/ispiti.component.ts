import {Component, Input} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {IspitService} from "./ispit.service";
import {Ispit} from "./ispit.model";
import {UtilsService} from "../../utils/utils.service";
import {Student} from "../studenti/student.model";
import {ActivatedRoute, Router} from "@angular/router";
import {StudentService} from "../studenti/student.service";

@Component({
  selector: 'app-ispiti',
  templateUrl: './ispiti.component.html',
})
export class IspitiComponent {

  @Input() student: Student | null = null;
  //@Input() editRoute: string = '/ispiti';

  ispiti: Ispit[] = [];

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

  constructor(private ispitService: IspitService, private studentService: StudentService, private modalService: NgbModal, public utils: UtilsService, private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const username = this.route.snapshot.paramMap.get('username') || '';
    this.params.studentUsername = username;
    if(username) {
      this.fetchStudentDetails(username);
    }
    this.ucitavanjeIspita();
  }

  ucitavanjeIspita(page = 0) {
    this.params.page = page;
    const username = this.params.studentUsername;
    if (username) {
      this.params.studentUsername = username;
    }
    this.ispitService.getAll(this.params).subscribe(response => {
      this.ispiti = response.body!;
      this.totalCount = +response.headers.get('X-TOTAL-COUNT')!;
    });
  }

  // editIspit(id: number): void {
  //   this.router.navigate([`${this.editRoute}/${id}`]);
  // }

  getEditLink(ispitId: number | undefined): string {
    const isStudentProfilePage = this.params.studentUsername;
    if (isStudentProfilePage && ispitId !== undefined) {
      //Edit sa profila studenta
      return `/profile/${isStudentProfilePage}/ispiti/${ispitId}`;
    } else {
      //Edit sa generalne stranice za ispite
      return `/ispiti/${ispitId}`;
    }
  }

  getDodavanjeLink(): string {
    const isStudentProfilePage = this.params.studentUsername;
    if (isStudentProfilePage) {
      return `/profile/${isStudentProfilePage}/ispit-dodavanje`;
    } else {
      return '/ispit-dodavanje';
    }
  }

  private fetchStudentDetails(username: string): void {
    this.studentService.get(username).subscribe(student => {
      this.ispiti.forEach(ispit => {
        ispit.student = student;
      });
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
    this.ucitavanjeIspita();
  }

}
