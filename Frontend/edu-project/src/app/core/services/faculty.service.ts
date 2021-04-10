import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FacultyService {
  private readonly baseURL = environment.url;

  constructor(private readonly http: HttpClient) { }

  getFacultyList(): Observable<any> {
    return this.http.get(`${this.baseURL}/faculty`);
  }
}
