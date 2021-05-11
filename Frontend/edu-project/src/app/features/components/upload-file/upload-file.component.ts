import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';
import { FileUploadService } from 'src/app/shared/services/file-upload.service';
import { Course } from 'src/app/shared/types/course';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent implements OnInit {
  @ViewChild('selectedDoc') selectedDoc: ElementRef;
  noFileSelected = true;
  selectedCourse: string;
  fileName = '';

  constructor(
    private readonly uploadService: FileUploadService,
    private errorService: ErrorHandlingService
  ) { }

  ngOnInit(): void {
    this.selectedCourse = localStorage.getItem('selectedCourseName')
  }

  onSubmit() {
    const file: File = this.selectedDoc.nativeElement.files[0];
    if (file) {
      this.uploadService.uploadFile(file, this.selectedCourse.replace(/\s/g,'')).subscribe(
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

  changeSelectedFile(event): void {
    this.noFileSelected = false;
    this.fileName = event.target.files[0].name;
  }


}
