import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UtilsService} from "../../utils/utils.service";
import {StudentService} from "../../components/studenti/student.service";
import {Student} from "../../components/studenti/student.model";

@Component({
  selector: 'app-student-profile',
  templateUrl: './student-profile.component.html',
})
export class StudentProfileComponent implements OnInit {

  username: string = '';
  student: Student | null = null;

  constructor(private studentService: StudentService, private auth: AuthService, private router: Router,
              public utils: UtilsService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.username = this.route.snapshot.paramMap.get('username')!;
    const username = this.auth.getUsernameFromToken();
    if (!this.username) {
      this.username = username;
    }

    if (!this.utils.hasAnyRole(['NASTAVNIK', 'ADMIN']) && this.username !== username) {
      this.router.navigate(['/']);
    }

    if (this.username) {
      this.studentService.get(this.username).subscribe(data => {
        this.student = data;
      });
    }
  }

}
