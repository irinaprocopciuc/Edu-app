import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CoursesService } from 'src/app/shared/services/courses.service';
import { Course } from 'src/app/shared/types/course';
import { UploadFileModel } from 'src/app/shared/types/upload-file-model';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit {
  activeUser;
  coursesList: Course[];

  courseSelected = false;
  selectedCourse: Course;
  noFileSelected = true;
  resourcesList: string[] = [];
  userId: string;
  isFileSelected = false;
  fileSelectedName: string;
  isUploadBtnClicked = false;

  constructor(
    private readonly router: Router,
    private readonly courseService: CoursesService,
    private readonly fileService: FileUploadService
  ) {}

  ngOnInit(): void {
    let url = this.router.url;
    this.activeUser = url.split('/').filter((item) => item !== '')[0];
    this.getUserCourses(this.activeUser);
    this.userId = localStorage.getItem('userId');
  }

  selectCourse(course: Course): void {
    this.courseSelected = true;
    this.selectedCourse = course;
    this.isFileSelected = false;
    localStorage.setItem('selectedCourseName', this.selectedCourse.name);
    this.router.navigate([`${this.userId}/homepage/courses`]);
    this.getCourseFiles(course.name.replace(/\s/g, ''));
  }

  seeFileDetails(file: string): void {
    this.router.navigate([`${this.userId}/homepage/courses`]);
    this.router.navigate([`${this.userId}/homepage/courses/file-details`]);
    this.fileService.setFileName(file);
    this.isFileSelected = true;
    this.fileSelectedName = file;
    this.isUploadBtnClicked = false;
  }

  goToUploadFilePage(): void {
    this.isUploadBtnClicked = true;
    this.isFileSelected = false;
    this.router.navigate([`${this.userId}/homepage/courses/upload`]);
  }

  private getCourseFiles(coursename: string): void {
    this.courseService.getCouseFiles(coursename).subscribe((filesList) => {
      this.resourcesList = filesList['response'];
    });
  }

  private getUserCourses(user: string): void {
    this.courseService.getCourses(user).subscribe((courses) => {
      this.coursesList = courses['response'];
    });
  }
}
