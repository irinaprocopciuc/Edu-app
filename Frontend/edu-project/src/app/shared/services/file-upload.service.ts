import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  private readonly baseURL = environment.url;
  fileName: string;

  constructor(private http: HttpClient) {}

  setFileName(file: string): void {
    this.fileName = file;
  }

  getFileName(): string {
    return this.fileName;
  }

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

  getFiles(courseName: string) {
    return this.http.get(`${this.baseURL}/getFiles?courseName=${courseName}`);
  }

  downloadFile(fileName: string, courseName: string) {
    return this.http.get(
      `${this.baseURL}/downloadFile/fileName=${fileName}&courseName=${courseName}`,
      { responseType: 'blob' as 'json' }
    );
  }
}
