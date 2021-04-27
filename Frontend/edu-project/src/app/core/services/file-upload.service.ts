import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { UploadFileModel } from '../types/upload-file-model';

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  private readonly baseURL = environment.url;

  constructor(private http: HttpClient) {}

  uploadFile(file: FormData, course: string) {
    return this.http.post(
      `${this.baseURL}/uploadFile`,
      {
        file: file,
        course: course,
      },
      {
        headers: new HttpHeaders({
          'Content-Type':
            'multipart/form-data; boundary=----WebKitFormBoundaryvlb7BC9EAvfLB2q5',
          reportProgress: 'true',
          responseType: 'text',
        }),
      }
    );
  }
}
