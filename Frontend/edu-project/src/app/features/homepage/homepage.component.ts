import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CoursesService } from 'src/app/core/services/courses.service';
import { LoginRegisterService } from 'src/app/core/services/login-register.service';
import { Course } from 'src/app/core/types/course';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  activeUser;
  coursesList: Course[];

  constructor(
    private readonly loginService: LoginRegisterService,
    private readonly router: Router,
    private readonly courseService: CoursesService
  ) { }

  ngOnInit(): void {
    let url = this.router.url;
    this.activeUser = url.split('/').filter(item => item !== '')[0];
    console.log(this.router.url, this.activeUser);
    this.getUserCourses(this.activeUser);
  }

  logout(): void {
    this.loginService.setActiveUser(null);
    this.router.navigate(['login']);
  }

  private getUserCourses(user: string): void {
    this.courseService.getCourses(user).subscribe(courses => {
      console.log(courses);
      this.coursesList = courses['response'];
    });
  }
}
