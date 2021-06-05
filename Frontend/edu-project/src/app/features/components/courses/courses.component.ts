import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CoursesService } from 'src/app/shared/services/courses.service';
import { Course } from 'src/app/shared/types/course';
import { UploadFileModel } from 'src/app/shared/types/upload-file-model';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';
import { ThesisService } from 'src/app/shared/services/thesis.service';

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
  isViewAssignmentBtnClicked = false;
  userType: string;
  isOnThesisPage = false;

  constructor(
    private readonly router: Router,
    private readonly courseService: CoursesService,
    private readonly fileService: FileUploadService
  ) {}

  ngOnInit(): void {
    let url = this.router.url;
    this.activeUser = url.split('/').filter((item) => item !== '')[0];
    this.userId = localStorage.getItem('userId');
    this.userType = localStorage.getItem('type');
    if(this.userType === 'student') {
      this.getUserCourses(this.activeUser);
    } else if (this.userType === 'teacher') {
      this.getCoursesForTeacher(this.activeUser);
    }
  }

  selectCourse(course: Course): void {
    this.courseSelected = true;
    this.selectedCourse = course;
    this.isFileSelected = false;
    this.isOnThesisPage = false;
    this.isUploadBtnClicked = false;
    this.isViewAssignmentBtnClicked = false;
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

  goToAssignmentsPage(): void {
    this.isFileSelected = false;
    this.isUploadBtnClicked = false;
    this.isViewAssignmentBtnClicked = true;
    this.router.navigate([`${this.userId}/homepage/courses/view`]);
  }

  goToUploadFilePage(): void {
    this.isUploadBtnClicked = true;
    this.isFileSelected = false;
    this.isViewAssignmentBtnClicked = false;
    this.router.navigate([`${this.userId}/homepage/courses/upload`]);
  }

  fileAdded(event): void {
    if(event) {
      this.getCourseFiles(this.selectedCourse.name.replace(/\s/g, ''));
    }
  }

  goToThesisPage(): void{
    this.router.navigate([`${this.userId}/homepage/courses/thesis`]);
    this.selectedCourse = null;
    this.isOnThesisPage = true;
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

  private getCoursesForTeacher(user:string): void {
    this.courseService.getCoursesForTeacher(user).subscribe((courses) => {
      this.coursesList = courses['response'];
    });
  }
}
