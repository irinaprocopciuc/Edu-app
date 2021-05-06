import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDetails } from 'src/app/features/types/user-details';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseURL = environment.url;

  constructor(private readonly http: HttpClient) { }

  getUsers(): Observable<UserDetails> {
    return this.http.get<UserDetails>(`${this.baseURL}/users`);
  }
}
