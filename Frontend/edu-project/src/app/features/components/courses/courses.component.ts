import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CoursesService } from 'src/app/shared/services/courses.service';
import { Course } from 'src/app/shared/types/course';
import { UploadFileModel } from 'src/app/shared/types/upload-file-model';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {
  activeUser;
  coursesList: Course[];
  fileName = '';
  courseSelected = false;
  selectedCourse: Course;

  constructor(
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
      formData.append('file', file, file.name);
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
