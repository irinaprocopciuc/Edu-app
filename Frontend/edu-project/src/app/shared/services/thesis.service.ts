import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ChooseTheme } from '../types/choose-theme';

@Injectable({
  providedIn: 'root'
})
export class ThesisService {
  baseUrl = environment.url;

  constructor(private http: HttpClient) { }

  getAllThesis(): Observable<any> {
    return this.http.get(`${this.baseUrl}/thesis/getAll`);
  }

  getChosenTheme(idStudent: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/thesis/getChosen/idStudent=${idStudent}`);
  }
  chooseTheme(theme: ChooseTheme): Observable<any> {
    return this.http.put(`${this.baseUrl}/thesis/chooseTheme`, {...theme});
  }
}
