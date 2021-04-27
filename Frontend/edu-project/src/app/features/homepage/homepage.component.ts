import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CoursesService } from 'src/app/core/services/courses.service';
import { FileUploadService } from 'src/app/core/services/file-upload.service';
import { LoginRegisterService } from 'src/app/core/services/login-register.service';
import { Course } from 'src/app/core/types/course';
import { UploadFileModel } from 'src/app/core/types/upload-file-model';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  activeUser;
  coursesList: Course[];
  fileName = '';
  courseSelected = false;
  selectedCourse: Course;

  constructor(
    private readonly loginService: LoginRegisterService,
    private readonly router: Router,
    private readonly courseService: CoursesService,
    private readonly uploadService: FileUploadService
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

  selectCourse(course: Course): void {
    this.courseSelected = true;
    this.selectedCourse = course;
    console.log(course);
  }

  onFileSelected(event) {
    const file:File = event.target.files[0];
    if (file) {
      let uploadFileObject: UploadFileModel = {file: null, course: ''};
      this.fileName = file.name;
      const formData = new FormData();
      formData.append('file', file);
      if (formData) {
        this.uploadService.uploadFile(formData, this.selectedCourse.name).subscribe(
          (res) => console.log(res),
          (err) => console.log(err)
        );
      }
      console.log(file);
    }
  }

  private getUserCourses(user: string): void {
    this.courseService.getCourses(user).subscribe(courses => {
      console.log(courses);
      this.coursesList = courses['response'];
    });
  }
}
