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
  @ViewChild('selectedDoc') selectedDoc: ElementRef;
  activeUser;
  coursesList: Course[];
  fileName = '';
  courseSelected = false;
  selectedCourse: Course;
  noFileSelected = true;

  constructor(
    private readonly router: Router,
    private readonly courseService: CoursesService,
    private readonly uploadService: FileUploadService,
    private errorService: ErrorHandlingService
  ) {}

  ngOnInit(): void {
    let url = this.router.url;
    this.activeUser = url.split('/').filter((item) => item !== '')[0];
    this.getUserCourses(this.activeUser);
  }

  selectCourse(course: Course): void {
    this.courseSelected = true;
    this.selectedCourse = course;
  }

  changeSelectedFile(event): void {
    this.noFileSelected = false;
    this.fileName = event.target.files[0].name;
  }

  onSubmit() {
    const file: File = this.selectedDoc.nativeElement.files[0];
    if (file) {
      this.uploadService.uploadFile(file, this.selectedCourse.name).subscribe(
        (res) => {
          this.errorService.displaySuccessToast(res['message'], '');
        },
        (err) => {
          this.errorService.displayErrorToast(err.error.message, '');
        }
      );
      this.noFileSelected = true;
    }
  }

  private getUserCourses(user: string): void {
    this.courseService.getCourses(user).subscribe((courses) => {
      this.coursesList = courses['response'];
    });
  }
}
