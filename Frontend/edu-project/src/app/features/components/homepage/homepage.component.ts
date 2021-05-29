import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRegisterService } from 'src/app/core/services/login-register.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  username: string;

  constructor(
    private readonly router: Router,
    private readonly loginservice: LoginRegisterService
  ) { }

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
  }

  logout(): void {
    localStorage.setItem('username', null);
    localStorage.setItem('userId', null);
    localStorage.setItem('type', null);
    this.loginservice.setActiveUser(null);
    this.router.navigate(['login']);
  }
}
