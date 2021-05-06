import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';
import { FacultyService } from 'src/app/core/services/faculty.service';
import { LoginRegisterService } from 'src/app/core/services/login-register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  faculties = [];
  specs = [];
  yearOfStudyArr = [
    {value: 1, viewValue: 1},
    {value: 2, viewValue: 2},
    {value: 3, viewValue: 3}
  ];
  semesterArr = [
    {value: 1, viewValue: 1},
    {value: 2, viewValue: 2}
  ]

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly router: Router,
    private readonly facultyService: FacultyService,
    private readonly registerService: LoginRegisterService,
    private readonly errorService: ErrorHandlingService
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      name: [null, Validators.required],
      username: [null, Validators.required],
      email: [null, Validators.required],
      password: [null, Validators.required],
      repassword: [null, Validators.required],
      userType: [null, Validators.required],
      yearOfStudy: [null, Validators.required],
      semester: [null, Validators.required],
      faculty: [null, Validators.required],
      specName: [null, Validators.required]
    });
    this.getFacultyandSpecialization();
  }

  register(info): void {
    this.registerService.registerUser(info).subscribe(
      (resp) => {
        if (resp['code'] === '200') {
          this.errorService.displaySuccessToast(resp['message'], '');
          this.router.navigate(['login']);
        }
    },
    err => {
      if (err.error.code === '401') {
        this.errorService.displayErrorToast(err.error.message, '');
      }
    });
  }

  onReset(): void {
    this.registerForm.reset();
  }

  backToLogin(): void {
    this.router.navigate(['login']);
  }

  private getFacultyandSpecialization(): void {
    this.facultyService.getFacultyList().subscribe(res => {
      let facultyList = res.response;
      facultyList.forEach(element => {
        this.faculties.push({value: element.faculty, viewValue: element.faculty});
        this.specs.push({value: element.specialization, viewValue: element.specialization})
      });
      this.faculties.filter((thing, index) =>{
        if (thing[index] === thing[index+1]) {

          this.faculties.pop();
        }
      });
    });
  }
}
