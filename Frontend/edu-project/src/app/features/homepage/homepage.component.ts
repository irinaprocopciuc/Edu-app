import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRegisterService } from 'src/app/core/services/login-register.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  constructor(
    private readonly loginService: LoginRegisterService,
    private readonly router: Router
  ) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.loginService.setActiveUser(null);
    this.router.navigate(['login']);
  }

}
