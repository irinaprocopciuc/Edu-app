import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';
import { LoginRegisterService } from 'src/app/core/services/login-register.service';
import { LoginResp } from '../../types/user-details';

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
    this.loginService.loginUser(this.loginForm.getRawValue()).subscribe(
      (resp: LoginResp) => {
        let userDetails = resp.userDetails[0];
        this.errorService.displaySuccessToast(resp.message, '');
        localStorage.setItem('username', userDetails.name);
        localStorage.setItem('userId', userDetails.iduser);
        this.loginService.setActiveUser(userDetails);
        this.router.navigate([`${userDetails.iduser}/homepage`]);
      },
      err => {
        if (err.error.code === '401') {
          this.errorService.displayErrorToast(err.error.message, '');
        }
      }
    );
  }

}
