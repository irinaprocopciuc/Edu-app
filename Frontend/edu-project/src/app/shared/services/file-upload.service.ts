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

  uploadFile(file: File, course: string) {
    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('course', course);

    return this.http.post(`${this.baseURL}/uploadFile`, formData, {
      headers: new HttpHeaders({
        Accept: 'application/json',
        reportProgress: 'true',
        responseType: 'text',
      }),
    });
  }
}
