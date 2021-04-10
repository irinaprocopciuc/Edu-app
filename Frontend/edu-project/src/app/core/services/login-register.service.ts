import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { LoginObject } from '../types/login-object';
import { RegisterObject } from '../types/register-object';

@Injectable({
  providedIn: 'root'
})
export class LoginRegisterService {
  private readonly baseURL = environment.url;

  constructor(private http: HttpClient) { }

  loginUser(loginObj: LoginObject) {
    if (loginObj.username !== '' && loginObj.password !== '') {
      return this.http.post(`${this.baseURL}/login/checkUser`, {
        ...loginObj,
      });
    }
  }

  registerUser(registerObj: RegisterObject) {
    return this.http.post(`${this.baseURL}/register/registerUser`, {
      ...registerObj,
    });
  }
}
