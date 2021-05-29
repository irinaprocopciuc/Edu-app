import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';
import { CoursesService } from 'src/app/shared/services/courses.service';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent implements OnInit {
  @ViewChild('selectedDoc') selectedDoc: ElementRef;
  @Output() fileAdded: EventEmitter<any> = new EventEmitter();

  noFileSelected = true;
  selectedCourse: string;
  fileName = '';
  userType: string;
  userId: string;
  projectsList: string[] = [];

  constructor(
    private readonly uploadService: FileUploadService,
    private readonly courseService: CoursesService,
    private errorService: ErrorHandlingService
  ) { }

  ngOnInit(): void {
    this.selectedCourse = localStorage.getItem('selectedCourseName');
    this.userType = localStorage.getItem('type');
    this.userId = localStorage.getItem('userId');
    if (this.userType === 'student') {
      this.getCourseProjects();
    }
  }

  onSubmit() {
    const file: File = this.selectedDoc.nativeElement.files[0];
    if (file) {
     if (this.userType === 'teacher') {
      this.uploadFile(file);
     } else if (this.userType === 'student') {
      this.uploadProject(file);
     }
      this.noFileSelected = true;
    }
  }

  private getCourseProjects(): void {
    this.courseService.getCourseProjects(this.selectedCourse.replace(/\s/g, ''), this.userId).subscribe(
      projectsRes => {
      this.projectsList = projectsRes.response;
      console.log(this.projectsList);
      this.errorService.displaySuccessToast(projectsRes['message'], '');
    })
  }

  private uploadFile(file: File) {
    this.uploadService.uploadFile(file, this.selectedCourse.replace(/\s/g, '')).subscribe(
      (res) => {
        this.errorService.displaySuccessToast(res['message'], '');
        this.fileAdded.emit(true);
      },
      (err) => {
        this.errorService.displayErrorToast(err.error.message, '');
      }
    );
  }

  private uploadProject(file: File) {
    this.uploadService.uploadProject(file, this.selectedCourse.replace(/\s/g, ''), this.userId).subscribe(
      (res) => {
        this.errorService.displaySuccessToast(res['message'], '');
        this.getCourseProjects();
      },
      (err) => {
        this.errorService.displayErrorToast(err.error.message, '');
      }
    );
  }

  changeSelectedFile(event): void {
    this.noFileSelected = false;
    this.fileName = event.target.files[0].name;
  }


}
