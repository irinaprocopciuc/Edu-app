import { ErrorHandlingService } from './../../core/services/error-handling.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRegisterService } from 'src/app/core/services/login-register.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly router: Router,
    private readonly loginService: LoginRegisterService,
    private readonly errorService: ErrorHandlingService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });
  }

  login(): void {
    console.log(this.loginForm.getRawValue());
    this.loginService.loginUser(this.loginForm.getRawValue()).subscribe(
      (resp) => {
        this.errorService.displaySuccessToast(resp['message'], '');
        this.loginService.setActiveUser(resp['userId']);
        this.router.navigate(['homepage']);
        this.loginService.getActiveUser().subscribe(res => {console.log(res)});
      },
      err => {
        if (err.error.code === '401') {
          this.errorService.displayErrorToast(err.error.message, '');
        }
      }
    );
  }

}
